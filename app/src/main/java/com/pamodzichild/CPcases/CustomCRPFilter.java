package com.pamodzichild.CPcases;

import android.widget.Filter;

import java.util.ArrayList;

/**
 * Created by corei3 on 09-07-2017.
 */

public class CustomCRPFilter extends Filter {

    MyCPAdapter adapter;
    ArrayList<ModelCPcases> filterList;

    public CustomCRPFilter(ArrayList<ModelCPcases> filterList, MyCPAdapter adapter) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        // CHECK CONSTRAINT VALIDITY
        if (constraint != null && constraint.length() > 0) {
            // CHANGE TO UPPER
            constraint = constraint.toString().toUpperCase();
            // STORE OUR FILTERED PLAYERS
            ArrayList<ModelCPcases> filteredPlayers = new ArrayList<>();
            for (int i = 0; i < filterList.size(); i++) {
                // CHECK
                if (filterList.get(i).getDateOfReporting().toUpperCase().contains(constraint)
                        ||filterList.get(i).getReporter().getReporterName().toUpperCase().contains(constraint)
                        ||filterList.get(i).getChildLocation().toUpperCase().contains(constraint)
                        ||filterList.get(i).getDetailsOfPerpetrator().toUpperCase().contains(constraint)
                        ||filterList.get(i).getCurrentStateOfChild().toUpperCase().contains(constraint)
                        ||filterList.get(i).getNameOfChild().toUpperCase().contains(constraint)
                        ||filterList.get(i).getGenderOfChild().toUpperCase().contains(constraint)) {
                    // ADD PLAYER TO FILTERED PLAYERS
                    filteredPlayers.add(filterList.get(i));
                }
            }
            results.count = filteredPlayers.size();
            results.values = filteredPlayers;
        } else {
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.models = (ArrayList<ModelCPcases>) results.values;
        // REFRESH
        adapter.notifyDataSetChanged();
    }
}
