package com.example.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Order> orderList = new ArrayList<>();
        orderList.add(new Order("SO00109", "Công ty TNHH CloudGO", "2.139.000 đ", "30/07/2024", "Chưa thanh toán", "Mới"));
        orderList.add(new Order("SO00110", "Công ty ABC", "5.000.000 đ", "01/08/2024", "Đã thanh toán", "Đã giao"));
        orderList.add(new Order("SO00111", "Công ty XYZ", "3.500.000 đ", "02/08/2024", "Đang xử lý", "ĐH B2C"));

        recyclerView.setAdapter(new OrderAdapter(orderList, this));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // 🔹 HÀM showBottomSheet() thêm ở đây
    public void showBottomSheet() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);

        // ✅ Sửa dòng này
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_actions, null, false);

        LinearLayout layoutActions = view.findViewById(R.id.layoutActions);

        addActionItem(layoutActions, R.drawable.keep, "Ghim", () ->
                Toast.makeText(this, "Đã ghim đơn hàng", Toast.LENGTH_SHORT).show());
        addActionItem(layoutActions, R.drawable.cached, "Chuyển thành hóa đơn", () ->
                Toast.makeText(this, "Chuyển hóa đơn thành công", Toast.LENGTH_SHORT).show());

        addActionItem(layoutActions, R.drawable.files, "Xuất file PDF", () ->
                Toast.makeText(this, "Xuất file PDF...", Toast.LENGTH_SHORT).show());
        addActionItem(layoutActions, R.drawable.outgoingmail, "Gửi email kèm file PDF", () ->
                Toast.makeText(this, "Gửi thành công", Toast.LENGTH_SHORT).show());
        addActionItem(layoutActions, R.drawable.copy, "Nhân đôi", () ->
                Toast.makeText(this, "Nhân đôi thành công", Toast.LENGTH_SHORT).show());

        addActionItem(layoutActions, R.drawable.cancel, "Hủy đơn hàng", () ->
                Toast.makeText(this, "Đơn hàng đã bị hủy", Toast.LENGTH_SHORT).show());

        view.findViewById(R.id.btnClose).setOnClickListener(v -> dialog.dismiss());

        dialog.setContentView(view);
        dialog.show();
    }

    // 🔹 Hàm tạo 1 item_action
    private void addActionItem(LinearLayout parent, int iconRes, String text, Runnable onClick) {
        View itemView = LayoutInflater.from(this).inflate(R.layout.item_action, parent, false);
        ImageView icon = itemView.findViewById(R.id.actionIcon);
        TextView label = itemView.findViewById(R.id.actionText);
        icon.setImageResource(iconRes);
        label.setText(text);

        itemView.setOnClickListener(v -> {
            onClick.run();
        });

        parent.addView(itemView);
    }
}


