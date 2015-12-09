package com.hcs.fastsales.sale;

import android.graphics.Color;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.hcs.common.activity.CommonActivity;
import com.hcs.common.utils.ViewUtils;
import com.hcs.fastsales.R;

/**
 * Created by fernando.ramirez on 09/07/2015.
 */
public class PayOrderActivity extends CommonActivity {

    private TableLayout tlListProds;

    public PayOrderActivity() {
        super(R.layout.activity_pay_order);
    }

    @Override
    protected void initViews() {
        tlListProds = (TableLayout) findViewById(R.id.tlListProds);
        tlListProds.addView(this.getRowTableHeader(), new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

        TableRow tr = new TableRow(this);
        tr.setBackgroundColor(Color.GRAY);
        tr.setId(100 + 1);
        tr.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));

        TextView tvProductName = new TextView(this);
        tvProductName.setId(200 + 1);
        tvProductName.setText("Product 1");
        tvProductName.setPadding(2, 0, 5, 0);
        tvProductName.setTextColor(Color.WHITE);
        tr.addView(tvProductName);

        TextView tvQuantity = new TextView(this);
        tvQuantity.setId(200 + 2);
        tvQuantity.setText("10");
        tvQuantity.setTextColor(Color.WHITE);
        tr.addView(tvQuantity);

        TextView tvPrice = new TextView(this);
        tvPrice.setId(200 + 3);
        tvPrice.setText("60.0");
        tvPrice.setTextColor(Color.WHITE);
        tr.addView(tvPrice);

        tlListProds.addView(tr, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
    }

    private TableRow getRowTableHeader() {
        TableRow trHeader = new TableRow(this);
        trHeader.setId(0);
        trHeader.setBackgroundColor(Color.LTGRAY);
        trHeader.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT));

        trHeader.addView(this.getTextViewForRowTableHeader(ViewUtils.getStringResource(this, R.string.txtProduct)));
        trHeader.addView(this.getTextViewForRowTableHeader(ViewUtils.getStringResource(this, R.string.txtQuantity)));
        trHeader.addView(this.getTextViewForRowTableHeader(ViewUtils.getStringResource(this, R.string.txtTPrice)));
        return trHeader;
    }

    private TextView getTextViewForRowTableHeader(String text) {
        TextView tvProductName = new TextView(this);
        tvProductName.setText(text);
        tvProductName.setTextColor(Color.WHITE);
        tvProductName.setPadding(10, 10, 10, 10);
        return tvProductName;
    }
}
