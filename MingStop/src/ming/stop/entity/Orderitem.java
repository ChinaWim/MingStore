package ming.stop.entity;

/**
 * Created by Ming on 2017/8/23.
 * 订单和商品的 关系表
 */
public class Orderitem {
    private String itemid;
    private int count;
    private double subtotal;
    private Product product;//商品
    private String pid;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    private Orders orders;//订单
    private String oid;

    @Override
    public String toString() {
        return "Orderitem{" +
                "itemid='" + itemid + '\'' +
                ", count=" + count +
                ", subtotal=" + subtotal +
                ", product=" + product +
                ", pid='" + pid + '\'' +
                ", orders=" + orders +
                ", oid='" + oid + '\'' +
                '}';
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }
}
