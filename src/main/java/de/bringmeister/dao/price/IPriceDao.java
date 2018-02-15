package de.bringmeister.dao.price;

import de.bringmeister.domain.Price;
import de.bringmeister.domain.ProductPrice;
import de.bringmeister.domain.Unit;

import java.util.List;

/**
 * Created by petermihal2 on 11/02/2018.
 */
public interface IPriceDao {

    List<ProductPrice> getPrices(String productSku);

    List<Price> getPrices(String productSku, Unit unit);
}
