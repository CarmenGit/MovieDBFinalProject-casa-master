package es.cice.moviedbfinalproject.adapters;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import es.cice.moviedbfinalproject.R;
import es.cice.moviedbfinalproject.model.Film;


/**
 * Created by cice on 20/1/17.
 */

public class FilmsAdapter extends RecyclerView.Adapter<FilmsAdapter.ViewHolder> implements Filterable {
    private List<Film> filmList;
    private Context ctx;


    public FilmsAdapter(Context ctx, List<Film> list){
        filmList=list;
        this.ctx=ctx;
    }
    /*
    Este metodo se llama cada vez que sea necesario construir una nueva fila
     */
    @Override
    public FilmsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Obtenemos el infater necesario para construir un fila definida en xml
        LayoutInflater inflater=LayoutInflater.from(ctx);
        View row=inflater.inflate(R.layout.film_row,parent,false);
        ViewHolder holder=new ViewHolder(row);


        return holder;
    }

    @Override
    public void onBindViewHolder(final FilmsAdapter.ViewHolder holder, int position) {
       // holder.filmImageIV.setImageResource(filmList.get(position).getMiniatura());
        holder.tituloTV.setText(filmList.get(position).getDescripcion() + " " +
                filmList.get(position).getModelo());


        holder.vElipsisTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Elipsis","onclick()...");
                PopupMenu popup=new PopupMenu(ctx,holder.vElipsisTV);
                popup.inflate(R.menu.car_popup);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()){
                            case R.id.deleteCarItem:
                                Log.d("Elipsis","delete...");
                                filmList.remove(holder.getAdapterPosition());
                                notifyDataSetChanged();
                                break;
                            case R.id.detailsCarItem:
                                break;
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return filmList.size();
    }

    @Override
    public Filter getFilter() {
        return new CarFilter();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView filmImageIV;
        private TextView tituloTV;
        private TextView vElipsisTV;

        public ViewHolder(View itemView) {
            super(itemView);
            filmImageIV= (ImageView) itemView.findViewById(R.id.filmImageIV);
            tituloTV= (TextView) itemView.findViewById(R.id.tituloTV);
            vElipsisTV= (TextView) itemView.findViewById(R.id.vElipsis);

            filmImageIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("CarViewHolder","old position: " + getOldPosition());
                    Log.d("CarViewHolder","layout position: " + getLayoutPosition());
                    Log.d("CarViewHolder","adapter position: " + getAdapterPosition());
                    /*Intent intent=new Intent(ctx,CarDetailActivity.class);
                    intent.putExtra(CarDetailActivity.IMAGEN_EXTRA,
                            carList.get(getAdapterPosition()).getImagen());
                    intent.putExtra(CarDetailActivity.DESCRIPCION_EXTRA,
                            carList.get(getAdapterPosition()).getDescripcion());
                    intent.putExtra(CarDetailActivity.FABRICANTE_EXTRA,
                            carList.get(getAdapterPosition()).getFabricante());
                    intent.putExtra(CarDetailActivity.MODELO_EXTRA,
               Log.d("Elipsis","onclick()...");             carList.get(getAdapterPosition()).getModelo());
                    ctx.startActivity(intent);*/
                }
            });
        }
    }

    public class CarFilter extends Filter{
        public static final String TAG="CarFilter";
        private List<Film> originalList;
        private List<Film> filteredList;

        public CarFilter(){
            originalList=new LinkedList<>(filmList);

        }
        @Override
        protected FilterResults performFiltering(CharSequence filterData) {
            Log.d(TAG,"performFiltering()...");
            String filterStr=filterData.toString();
            FilterResults results=new FilterResults();
            filteredList=new ArrayList<>();
            for(Film f:originalList){
                if(f.getFabricante().equalsIgnoreCase(filterStr) ||
                        f.getModelo().equalsIgnoreCase(filterStr)){
                    filteredList.add(f);

                }
            }
            results.values=filteredList;
            results.count=filteredList.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            Log.d(TAG,"publishResults()...");
            List<Film>list=(List<Film>) filterResults.values;
            if(list.size()>0)
                filmList=list;
            notifyDataSetChanged();
        }
    }
}

