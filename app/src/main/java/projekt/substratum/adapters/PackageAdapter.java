/*
 * Copyright (c) 2016-2017 Projekt Substratum
 * This file is part of Substratum.
 *
 * Substratum is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Substratum is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Substratum.  If not, see <http://www.gnu.org/licenses/>.
 */

package projekt.substratum.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;

import java.util.ArrayList;

import projekt.substratum.R;
import projekt.substratum.config.References;
import projekt.substratum.model.PackageInfo;

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.ViewHolder> {
    private ArrayList<PackageInfo> information;

    public PackageAdapter(ArrayList<PackageInfo> information) {
        this.information = information;
    }

    @Override
    public PackageAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.package_entry, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int pos) {
        String packageName = information.get(pos).getPackageName();
        if (packageName.endsWith(".common")) {
            packageName = packageName.substring(0, packageName.length() - 7);
        }

        viewHolder.packName.setText(
                new StringBuilder().append(
                        References.grabPackageName(information.get(pos).getContext(), packageName))
                        .append((information.get(pos).getCommons()) ? " " +
                                information.get(pos).getContext().getString(
                                        R.string.resource_checker_commons) : "").toString());

        viewHolder.packIcon.setImageDrawable(References.grabAppIcon(information.get(pos)
                        .getContext(),
                packageName));

        if (information.get(pos).getVerification()) {
            viewHolder.verificationIcon.setImageDrawable(
                    information.get(pos).getContext().getDrawable(
                            R.drawable.package_verification_success));
            viewHolder.verificationText.setText(
                    information.get(pos).getContext().getString(R.string.resource_validated));
            viewHolder.numberProgressBar.setProgress(100);
        } else {
            viewHolder.numberProgressBar.setProgressTextColor(
                    information.get(pos).getContext().getColor(
                            R.color.number_progress_bar_validation_error));
            viewHolder.numberProgressBar.setReachedBarColor(
                    information.get(pos).getContext().getColor(
                            R.color.number_progress_bar_validation_error));
            viewHolder.numberProgressBar.setProgress(information.get(pos).getPercentage());
        }
    }

    @Override
    public int getItemCount() {
        return information.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView packName;
        TextView verificationText;
        ImageView packIcon;
        ImageView verificationIcon;
        NumberProgressBar numberProgressBar;

        ViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.pack_card);
            packIcon = (ImageView) view.findViewById(R.id.pack_icon);
            verificationIcon = (ImageView) view.findViewById(R.id.verification);
            packName = (TextView) view.findViewById(R.id.pack_name);
            verificationText = (TextView) view.findViewById(R.id.verification_text);
            numberProgressBar = (NumberProgressBar) view.findViewById(R.id.number_progress_bar);
        }
    }
}