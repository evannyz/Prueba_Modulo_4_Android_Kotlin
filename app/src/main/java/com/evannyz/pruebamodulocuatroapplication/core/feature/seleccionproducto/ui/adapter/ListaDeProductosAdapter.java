package com.evannyz.pruebamodulocuatroapplication.core.feature.seleccionproducto.ui.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.evannyz.pruebamodulocuatroapplication.R;
import com.evannyz.pruebamodulocuatroapplication.core.feature.seleccionproducto.data.model.Producto;
import com.evannyz.pruebamodulocuatroapplication.databinding.ItemProductosLayoutBinding;


import java.util.List;

public class ListaDeProductosAdapter extends RecyclerView.Adapter<ListaDeProductosAdapter.ListaProductosViewHolder> {

    private List<Producto> listaDeProductos;
    private OnReciclerViewClickListener listener;


    public ListaDeProductosAdapter(List<Producto> listaDeProductos) {
        this.listaDeProductos = listaDeProductos;
    }

    @NonNull
    @Override
    public ListaProductosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_productos_layout, parent, false);
        return new ListaProductosViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaProductosViewHolder holder, int position) {
        holder.binData(listaDeProductos.get(position));
    }

    @Override
    public int getItemCount() {
        return listaDeProductos.size();
    }


    class ListaProductosViewHolder extends RecyclerView.ViewHolder {

        private ItemProductosLayoutBinding binding;
        private OnReciclerViewClickListener listener;

        public ListaProductosViewHolder(@NonNull View itemView, OnReciclerViewClickListener listener) {
            super(itemView);
            binding = ItemProductosLayoutBinding.bind(itemView);
            this.listener = listener;
        }

        public void binData(Producto productos) {
            binding.tvNombreProducto.setText(productos.getName());
            binding.tvIngredientes.setText(productos.getDescription());
            binding.tvPrecioProducto.setText("$" + productos.getPrice().toString());

            Glide.with(itemView).load(productos.getUrlImage())
                    .circleCrop()
                    .into(binding.ivProductosComestibles);

            itemView.setOnClickListener(v -> {
                listener.onRecyclerItemClick(itemView, getAdapterPosition(), productos.getPrice(), productos.getState());
                if (itemView.isPressed() && !productos.getState()) {
                    productos.setState(true);
                    notifyItemChanged(getLayoutPosition());
                } else if (itemView.isPressed() && productos.getState()) {
                    productos.setState(false);
                    notifyItemChanged(getLayoutPosition());
                }
            });
            if (!productos.getState()) {
                binding.ivClickBoton.setVisibility(View.INVISIBLE);
            } else {
                binding.ivClickBoton.setVisibility(View.VISIBLE);
            }
        }
    }

    public interface OnReciclerViewClickListener {
        void onRecyclerItemClick(View view, int position, int precio, boolean estado);
    }

    public void setListener(OnReciclerViewClickListener listener) {
        this.listener = listener;
    }
}
