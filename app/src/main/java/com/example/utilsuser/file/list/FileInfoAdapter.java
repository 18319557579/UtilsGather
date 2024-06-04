package com.example.utilsuser.file.list;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utilsuser.R;

import java.util.List;

public class FileInfoAdapter extends RecyclerView.Adapter<FileInfoAdapter.FileInfoHolder> {
    private List<FileInfoBean> fileInfoBeans;

    public FileInfoAdapter(List<FileInfoBean> fileInfoBeans) {
        this.fileInfoBeans = fileInfoBeans;
    }

    @NonNull
    @Override
    public FileInfoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(viewGroup.getContext(), R.layout.file_item_rv, null);
        return new FileInfoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileInfoHolder fileInfoHolder, int i) {
        fileInfoHolder.tvFileName.setText(fileInfoBeans.get(i).getName());
        fileInfoHolder.tvFilePath.setText(fileInfoBeans.get(i).getPath());
        fileInfoHolder.tvFileLength.setText(fileInfoBeans.get(i).getFormattedLength());
        fileInfoHolder.tvLastModified.setText(fileInfoBeans.get(i).getFormattedLastModified());
    }

    @Override
    public int getItemCount() {
        return fileInfoBeans == null ? 0 : fileInfoBeans.size();
    }

    public static class FileInfoHolder extends RecyclerView.ViewHolder {
        private TextView tvFileName;
        private TextView tvFilePath;
        private TextView tvFileLength;
        private TextView tvLastModified;

        public FileInfoHolder(@NonNull View itemView) {
            super(itemView);
            tvFileName = itemView.findViewById(R.id.tv_file_name);
            tvFilePath = itemView.findViewById(R.id.tv_file_path);
            tvFileLength = itemView.findViewById(R.id.tv_file_length);
            tvLastModified = itemView.findViewById(R.id.tv_file_last_modified);
        }
    }
}
