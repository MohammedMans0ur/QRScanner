package com.ebe.qrscanner.ui.favorites.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ebe.qrscanner.R;
import com.ebe.qrscanner.databinding.ItemQrScannedBinding;
import com.ebe.qrscanner.model.data.dto.QRItemDTO;
import com.ebe.qrscanner.utils.Constants;

import java.util.List;

public class ScannedItemsAdapter extends RecyclerView.Adapter<ScannedItemsAdapter.ScannedItemViewHolder> {
    private List<QRItemDTO> qrItemDTOList;
    private Context context;
    private OnScannedItemClickListener onScannedItemClickListener;

    public void setOnScannedItemClickListener(OnScannedItemClickListener onScannedItemClickListener) {
        this.onScannedItemClickListener = onScannedItemClickListener;
    }

    public ScannedItemsAdapter(List<QRItemDTO> qrItemDTOList, Context context) {
        this.qrItemDTOList = qrItemDTOList;
        this.context = context;
    }

    @NonNull
    @Override
    public ScannedItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ScannedItemsAdapter.ScannedItemViewHolder(ItemQrScannedBinding.inflate
                (LayoutInflater.from(parent.getContext()), parent, false).getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ScannedItemViewHolder holder, int position) {
        try {
            QRItemDTO itemDTO = qrItemDTOList.get(holder.getAdapterPosition());
            if (itemDTO != null) {
                holder.binding.txtItemQrContent.setText(itemDTO.getContent());
                holder.binding.txtItemQrDate.setText(itemDTO.getDate());
                holder.binding.imgItemQrImage.setImageDrawable(itemDTO.getType().contentEquals(Constants.SCANNED_QR_CODE_TYPE) ? context.getDrawable(R.drawable.baseline_qr_code_24) : context.getDrawable(R.drawable.ic_barcode));
                holder.binding.viewItemDivider.setVisibility(holder.getAdapterPosition() == qrItemDTOList.size() - 1 ? View.GONE : View.VISIBLE);
                holder.binding.getRoot().setOnClickListener(v -> {
                    if (onScannedItemClickListener != null)
                        onScannedItemClickListener.onScannedItemClick(itemDTO);
                });
            }
        } catch (Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        return qrItemDTOList == null ? 0 : qrItemDTOList.size();
    }

    public void clearAdapter() {
        try {
            int size = qrItemDTOList.size();
            qrItemDTOList.clear();
            notifyItemRangeRemoved(0, size);
        } catch (Exception e) {
        }
    }

    public static class ScannedItemViewHolder extends RecyclerView.ViewHolder {
        ItemQrScannedBinding binding;

        public ScannedItemViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemQrScannedBinding.bind(itemView);
        }
    }

    public interface OnScannedItemClickListener {
        void onScannedItemClick(QRItemDTO qrItemDTO);
    }
}
