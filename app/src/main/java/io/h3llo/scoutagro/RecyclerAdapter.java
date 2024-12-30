package io.h3llo.scoutagro;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements Filterable {

    String[] data1;
    String[] data2;
    int images[];
    Context context;

    // List<String> movieListAll;
    List<SectorEstadistico> sectoresList;
    List<SectorEstadistico> sectoresListAll;

    public RecyclerAdapter(List<SectorEstadistico> sectoresList) {
        this.sectoresList = sectoresList; // ESTA LISTA SERA FILTRADA
        this.sectoresListAll = new ArrayList<>(sectoresList); // ESTA LISTA TIENE TODOS LOS SECTORES ESTADISTICOS
    }

    //public RecyclerAdapter(Context ct, String s1[], String s2[], int img[]){
    public RecyclerAdapter(Context ct, List<SectorEstadistico> sectores ){

        context = ct;
//        data1 = s1;
//        data2 = s2;
//        images = img;

        // this.movieListAll = new ArrayList<>((movieList))
        this.sectoresList = new ArrayList<>(sectores);
        this.sectoresListAll = new ArrayList<>(sectores);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_item, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.myText1.setText(data1[position]);
//        holder.myText2.setText(data2[position]);
//        holder.myImage.setImageResource(images[position]);

        holder.departamento.setText(sectoresList.get(position).departamento);
        holder.provincia.setText(sectoresList.get(position).provincia);
        holder.distrito.setText(sectoresList.get(position).distrito);
        holder.sectorEstadistico.setText(sectoresList.get(position).sector_nombre);
        holder.cultivo.setText(sectoresList.get(position).cultivo);
        holder.supAsegHas.setText(sectoresList.get(position).superf_aseg_has);
        holder.rendPromKgXHa.setText(sectoresList.get(position).rend_prom_kg_x_ha);
        holder.rendDisp.setText(sectoresList.get(position).rend_disp);
        holder.nomFile.setText(sectoresList.get(position).nom_file); // ALMACENAR EL NOMBRE DEL ARCHIVO


    }

    @Override
    public int getItemCount() {
        return sectoresList.size();


        // return images.length;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {

        // RUNS ON A BACKGROUND THREAD AUTOMATICALLY
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String searchText=constraint.toString().toLowerCase();
            List<SectorEstadistico> filteredList = new ArrayList<>();

            if(searchText.length()==0 || searchText.isEmpty() ){
                filteredList.addAll(sectoresListAll);
            } else {
                for(SectorEstadistico item: sectoresListAll){

                    if(item.getSector_nombre().toLowerCase().contains(searchText)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        // RUNS ON A UI THREAD
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            sectoresList.clear();
            sectoresList.addAll((Collection<?  extends SectorEstadistico>) results.values);

            notifyDataSetChanged();

        }
    };



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView myText1;
        TextView myText2;
        ImageView myImage;

        TextView camp; // campaña 2020-2021
        TextView departamento; // Departamento
        TextView provincia; // Provincia
        TextView distrito; // Distrito
        TextView sectorEstadistico; // Sector Estadistico
        TextView cultivo;  // Cultivo
        TextView supAsegHas; // Superficie Asegurada (ha)
        TextView rendPromKgXHa; // Rendimiento Promedio 5 últimas campañas agricolas (Kg/ha)
        TextView rendDisp; // Rendimiento Disparador (56%) (Kg/ha)
        TextView nomFile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //myText1 = itemView.findViewById(R.id.myText1);
            //myText2 = itemView.findViewById(R.id.myText2);
            myImage = itemView.findViewById(R.id.myImageView);

            camp = itemView.findViewById(R.id.campa);
            departamento = itemView.findViewById(R.id.departamento);
            provincia = itemView.findViewById(R.id.provinc);
            distrito = itemView.findViewById(R.id.distrito);
            sectorEstadistico = itemView.findViewById(R.id.sector_est);
            cultivo = itemView.findViewById(R.id.cultivo);
            supAsegHas = itemView.findViewById(R.id.sup_aseg);
            rendPromKgXHa = itemView.findViewById(R.id.rend_prom);
            rendDisp = itemView.findViewById(R.id.rend_disp);
            nomFile = itemView.findViewById(R.id.nom_file);

            itemView.setOnClickListener(this);

            itemView.setOnLongClickListener(new View.OnLongClickListener(){

                @Override
                public boolean onLongClick(View v) {
                    //sectoresListAll.remove(getAdapterPosition());
                    //notifyItemRemoved(getAdapterPosition());
                    //return true;
                    return false;
                }
            });


        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), sectoresList.get(getAdapterPosition()).sector_nombre.toString()+" - "+sectoresList.get(getAdapterPosition()).cultivo.toString(), Toast.LENGTH_SHORT).show();

            String departamento = this.departamento.getText().toString();
            String provincia = this.provincia.getText().toString();
            String distrito = this.distrito.getText().toString();
            String sectorEstadistico = this.sectorEstadistico.getText().toString();
            String cultivo = this.cultivo.getText().toString();
            String supAsegHas = this.supAsegHas.getText().toString();
            String rendPromKgXHa = this.rendPromKgXHa.getText().toString();
            String rendDisp = this.rendDisp.getText().toString();
            String nomFile = this.nomFile.getText().toString();


            Intent i = new Intent(v.getContext(), SectorActivity.class);
            i.putExtra("departamento",departamento);
            i.putExtra("provincia",provincia);
            i.putExtra("distrito",distrito);
            i.putExtra("sectorEstadistico",sectorEstadistico);
            i.putExtra("cultivo",cultivo);
            i.putExtra("supAsegHas",supAsegHas);
            i.putExtra("rendPromKgXHa",rendPromKgXHa);
            i.putExtra("rendDisp",rendDisp);
            i.putExtra("nomFile",  nomFile);

            context.startActivity(i);
        }
    }
}
