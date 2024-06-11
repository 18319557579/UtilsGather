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
import com.example.utilsuser.file.list.database.state.BeanPackaged;
import com.example.utilsuser.file.list.database.state.FinishedState;
import com.example.utilsuser.file.list.database.state.PausedState;
import com.example.utilsuser.file.list.database.state.DownloadingState;

import java.util.ArrayList;
import java.util.List;

public class DownloaTaskAdapter extends RecyclerView.Adapter<DownloaTaskAdapter.FileInfoHolder> {

    // 点击事件接口
    private OnRecyclerItemClickListener mOnItemClickListener;

    // 设置点击事件监听器
    public void setRecyclerItemClickListener(OnRecyclerItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    // 点击事件接口定义
    public interface OnRecyclerItemClickListener {
        void onRecyclerItemClick(int position);
        void onTaskToPause(int id);
        void onTaskToResume(DownloadTaskBean downloadTaskBean);
    }

    private final List<BeanPackaged> downloadTaskBeans;

    public DownloaTaskAdapter(List<DownloadTaskBean> downloadTaskBeans) {
        this.downloadTaskBeans = new ArrayList<>();

        for (DownloadTaskBean downloadTaskBean : downloadTaskBeans) {
            BeanPackaged beanPackaged = new BeanPackaged();
            beanPackaged.downloadTaskBean = downloadTaskBean;

            if (downloadTaskBean.getCurrentLength() == downloadTaskBean.getTotalLength()) {
                beanPackaged.baseState = new FinishedState();
            } else {
                beanPackaged.baseState = new PausedState();
            }

            this.downloadTaskBeans.add(beanPackaged);
        }
    }

    @NonNull
    @Override
    public FileInfoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(viewGroup.getContext(), R.layout.file_download_task_item, null);
        return new FileInfoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileInfoHolder fileInfoHolder, int i) {
        fileInfoHolder.tvFileName.setText(downloadTaskBeans.get(i).downloadTaskBean.getShowName());
        fileInfoHolder.tvFileUrl.setText(downloadTaskBeans.get(i).downloadTaskBean.getUrl());
        fileInfoHolder.tvFilePath.setText(downloadTaskBeans.get(i).downloadTaskBean.getPath());

        fileInfoHolder.tvFileLength.setText(
                FormatTransfer.byteFormat(downloadTaskBeans.get(i).downloadTaskBean.getCurrentLength()) +
                " / " +
                FormatTransfer.byteFormat(downloadTaskBeans.get(i).downloadTaskBean.getTotalLength())
        );

        int progress = (int) (downloadTaskBeans.get(i).downloadTaskBean.getCurrentLength() * 100 / downloadTaskBeans.get(i).downloadTaskBean.getTotalLength());
        fileInfoHolder.pbDownloading.setProgress(progress);

        fileInfoHolder.tvStatus.setText(downloadTaskBeans.get(i).baseState.text);

        if (downloadTaskBeans.get(i).baseState instanceof FinishedState) {
            fileInfoHolder.ivOperation.setVisibility(View.GONE);
        } else {
            fileInfoHolder.ivOperation.setImageResource(downloadTaskBeans.get(i).baseState.resId);
        }
    }

    @Override
    public int getItemCount() {
        return downloadTaskBeans == null ? 0 : downloadTaskBeans.size();
    }

    public class FileInfoHolder extends RecyclerView.ViewHolder {
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

            ivOperation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    mOnItemClickListener.onRecyclerItemClick(getAdapterPosition());
                    BeanPackaged beanPackaged = downloadTaskBeans.get(getAdapterPosition());
                    if (beanPackaged.baseState instanceof DownloadingState) {
                        mOnItemClickListener.onTaskToPause(beanPackaged.downloadTaskBean.getId());

                    } else if (beanPackaged.baseState instanceof PausedState) {
                        mOnItemClickListener.onTaskToResume(beanPackaged.downloadTaskBean);
                    }
                }
            });
        }
    }

    public void notifyUpdateProgress(int id, long currentLength) {
        for (int i = 0; i < downloadTaskBeans.size(); i++) {
            BeanPackaged beanPackaged = downloadTaskBeans.get(i);
            if (beanPackaged.downloadTaskBean.getId() == id) {
                beanPackaged.downloadTaskBean.setCurrentLength(currentLength);

                //如果当前状态不是下载中的话，就更新。这样防止频繁更新
                if (! (beanPackaged.baseState instanceof DownloadingState)) {
                    beanPackaged.baseState = new DownloadingState();
                }

                notifyItemChanged(i);
                break;
            }
        }
    }

    public void notifyAdd(DownloadTaskBean downloadTaskBean) {
        BeanPackaged beanPackaged = new BeanPackaged();
        beanPackaged.downloadTaskBean = downloadTaskBean;
        beanPackaged.baseState = new PausedState();

        downloadTaskBeans.add(beanPackaged);
        notifyItemInserted(downloadTaskBeans.size() -1);
    }

    public void notifyPause(int id) {
        for (int i = 0; i < downloadTaskBeans.size(); i++) {
            BeanPackaged beanPackaged = downloadTaskBeans.get(i);
            if (beanPackaged.downloadTaskBean.getId() == id) {
                beanPackaged.baseState = new PausedState();
                notifyItemChanged(i);
                break;
            }
        }
    }

    public void notifyFinished(int id) {
        for (int i = 0; i < downloadTaskBeans.size(); i++) {
            BeanPackaged beanPackaged = downloadTaskBeans.get(i);
            if (beanPackaged.downloadTaskBean.getId() == id) {
                beanPackaged.baseState = new FinishedState();
                notifyItemChanged(i);
                break;
            }
        }
    }
}
