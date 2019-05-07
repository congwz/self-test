import com.viverselftest.util.StrUtils;
import lombok.Data;

/**
 * Created by Congwz on 2019/4/18.
 */
@Data
public class TestDTO {
    private String picking_no;
    private String warehousing_pick;


    public String trimNull() {
        if(StrUtils.isEmpty(warehousing_pick)) {
            warehousing_pick = "";
        }else {
            warehousing_pick = warehousing_pick.trim();
        }
        return warehousing_pick;
    }
}
