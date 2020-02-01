package cn.yi.bookstore.cart.bean;

import cn.yi.bookstore.book.bean.Book;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
    private Map<String, CartItem> cartItemMap = new LinkedHashMap<>();

    /**
     * 购物车商品总价
     * @return
     */
    public double getTotalPrice() {
        return cartItemMap.values().stream().mapToDouble(CartItem::getSubtotalPrice).sum();
    }

    /**
     * 添加条目到购物车中
     * @param cartItem
     */
    public void add(CartItem cartItem) {
        if(cartItem == null) {
            return;
        }
        Book book = cartItem.getBook();
        if (book == null) {
            return;
        }
        String bid = book.getBid();
        cartItemMap.compute(bid, (key, oldValue) ->{
           int oldNum = oldValue == null ? 0 : oldValue.getCount();
           cartItem.incrCount(oldNum);
           return cartItem;
        });
    }

    /**
     * 清空购物车
     */
    public void clear() {
        cartItemMap.clear();
    }

    /**
     * 删除条目
     */
    public void delete(String bid) {
        cartItemMap.remove(bid);
    }

    public Collection<CartItem> getCartItems() {
        return cartItemMap.values();
    }
}
