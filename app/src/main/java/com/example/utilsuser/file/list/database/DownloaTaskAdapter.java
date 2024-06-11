package com.example.utilsuser.file.list.database;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utilsgather.format_trans.FormatTransfer;
import com.example.utilsuser.R;

import java.util.List;

public class DownloaTaskAdapter extends RecyclerView.Adapter<DownloaTaskAdapter.FileInfoHolder> {
    private final List<DownloadTaskBean> downloadTaskBeans;

    public DownloaTaskAdapter(List<DownloadTaskBean> downloadTaskBeans) {
        this.downloadTaskBeans = downloadTaskBeans;
    }

    @NonNull
    @Override
    public FileInfoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(viewGroup.getContext(), R.layout.file_download_task_item, null);
        return new FileInfoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileInfoHolder fileInfoHolder, int i) {
        fileInfoHolder.tvFileName.setText(downloadTaskBeans.get(i).getShowName());
        fileInfoHolder.tvFileUrl.setText(downloadTaskBeans.get(i).getUrl());
        fileInfoHolder.tvFilePath.setText(downloadTaskBeans.get(i).getPath());

        fileInfoHolder.tvFileLength.setText(
                FormatTransfer.byteFormat(downloadTaskBeans.get(i).getCurrentLength()) +
                " / " +
                FormatTransfer.byteFormat(downloadTaskBeans.get(i).getTotalLength())
        );

        int progress = (int) (downloadTaskBeans.get(i).getCurrentLength() * 100 / downloadTaskBeans.get(i).getTotalLength());
        fileInfoHolder.pbDownloading.setProgress(progress);
    }

    @Override
    public int getItemCount() {
        return downloadTaskBeans == null ? 0 : downloadTaskBeans.size();
    }

    public static class FileInfoHolder extends RecyclerView.ViewHolder {
        private ProgressBar pbDownloading;
        private TextView tvFileName;
        private TextView tvFileUrl;
        private TextView tvFilePath;
        private TextView tvFileLength;
        private TextView tvStatus;
        private Button btnClear;
        private ImageView ivOperation;

        public FileInfoHolder(@NonNull View itemView) {
            super(itemView);
            pbDownloading = itemView.findViewById(R.id.pb_downloading_download_task);
            tvFileName = itemView.findViewById(R.id.tv_file_name_download_task);
            tvFileUrl = itemView.findViewById(R.id.tv_file_url_download_task);
            tvFilePath = itemView.findViewById(R.id.tv_file_path_download_task);
            tvFileLength = itemView.findViewById(R.id.tv_file_length_download_task);
            tvStatus = itemView.findViewById(R.id.tv_status_download_task);
            btnClear = itemView.findViewById(R.id.btn_clear_download_task);
            ivOperation = itemView.findViewById(R.id.btn_operation_download_task);
        }
    }

    public void notifyUpdateProgress(int id, long currentLength) {
        for (int i = 0; i < downloadTaskBeans.size(); i++) {
            DownloadTaskBean downloadTaskBean = downloadTaskBeans.get(i);
            if (downloadTaskBean.getId() == id) {
                downloadTaskBean.setCurrentLength(currentLength);
                notifyItemChanged(i);
                break;
            }
        }
    }

    public void notifyAdd(DownloadTaskBean downloadTaskBean) {
        downloadTaskBeans.add(downloadTaskBean);
        notifyItemInserted(downloadTaskBeans.size() -1);
    }
}
