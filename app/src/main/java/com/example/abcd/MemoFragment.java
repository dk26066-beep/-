package com.example.abcd;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MemoFragment extends Fragment {

    private TextView tvSelectedDate;
    private TextInputEditText etDateMemo;
    private TextView tvSavedMemoDisplay;

    private String selectedDateStr = "";
    private final Map<String, String> memoMap = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memo, container, false);

        CalendarView calendarView = view.findViewById(R.id.calendarView);
        tvSelectedDate = view.findViewById(R.id.tvSelectedDate);
        etDateMemo = view.findViewById(R.id.etDateMemo);
        MaterialButton btnSaveDateMemo = view.findViewById(R.id.btnSaveDateMemo);
        tvSavedMemoDisplay = view.findViewById(R.id.tvSavedMemoDisplay);

        calendarView.setOnDateChangeListener((v, year, month, dayOfMonth) -> {
            selectedDateStr = String.format(Locale.getDefault(), "%d년 %d월 %d일", year, month + 1, dayOfMonth);
            tvSelectedDate.setText(getString(R.string.selected_date_memo_format, selectedDateStr));

            String savedMemo = memoMap.get(selectedDateStr);
            if (savedMemo != null && !savedMemo.isEmpty()) {
                etDateMemo.setText(savedMemo);
                tvSavedMemoDisplay.setText(savedMemo);
            } else {
                etDateMemo.setText("");
                tvSavedMemoDisplay.setText("해당 날짜에 등록된 메모가 없습니다.");
            }
        });

        btnSaveDateMemo.setOnClickListener(v -> {
            String memoText = etDateMemo.getText() != null ? etDateMemo.getText().toString().trim() : "";
            if (selectedDateStr.isEmpty()) {
                Toast.makeText(requireContext(), "먼저 달력에서 날짜를 선택해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!memoText.isEmpty()) {
                memoMap.put(selectedDateStr, memoText);
                tvSavedMemoDisplay.setText(memoText);
                Toast.makeText(requireContext(), selectedDateStr + " 메모가 저장되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                memoMap.remove(selectedDateStr);
                tvSavedMemoDisplay.setText("해당 날짜에 등록된 메모가 없습니다.");
                Toast.makeText(requireContext(), "메모가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}