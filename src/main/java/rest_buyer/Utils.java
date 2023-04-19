package rest_buyer;

import com.example.DistributedAssignment.services.SaleItem;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public interface Utils {

    int CUSTOMER_DB_PORT = 8089;

    int PRODUCT_DB_PORT = 8085;

    static List<SaleItemPojo> getSaleItemsForRestResponse(Iterator<SaleItem> values){
        List<SaleItemPojo> saleItems = new ArrayList<>();

        while (values.hasNext()) {
            SaleItem value = values.next();
            SaleItemPojo saleItem = new SaleItemPojo();
            saleItem.setSellerID(value.getSellerID());
            saleItem.setItemID(value.getItemID());
            saleItem.setItemName(value.getItemName());
            saleItem.setItemPrice(value.getItemPrice());
            saleItem.setQuantity(value.getQuantity());
            saleItem.setIsNew(value.getIsNew());
            saleItem.setKeyWords(value.getKeyWords());
            saleItems.add(saleItem);
        }

        return saleItems;
    }
}
