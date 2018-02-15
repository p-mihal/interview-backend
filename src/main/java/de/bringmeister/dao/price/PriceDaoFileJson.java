package de.bringmeister.dao.price;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.bringmeister.domain.Price;
import de.bringmeister.domain.ProductPrice;
import de.bringmeister.domain.Unit;
import de.bringmeister.exceptions.AppInitException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by petermihal2 on 11/02/2018.
 */
@Component
public class PriceDaoFileJson implements IPriceDao{

    private Map<String, List<ProductPrice>> productSkuToPricesMap = null;

    @PostConstruct
    public void initPricesMap() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("products/prices.json").getFile());

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<ProductPrice> productPrices = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, ProductPrice.class));

            productSkuToPricesMap = productPrices.stream().collect(Collectors.groupingBy(ProductPrice::getProductSku));

        } catch (IOException e) {
            throw new AppInitException(e);
        }
    }

    @Override
    public List<ProductPrice> getPrices(String productSku){

        if(productSkuToPricesMap == null){
            initPricesMap();
        }

        if(!productSkuToPricesMap.containsKey(productSku)){
            return null;
        }

        return productSkuToPricesMap.get(productSku);

    }

    @Override
    public List<Price> getPrices(String sku, Unit unit) {
        return productSkuToPricesMap.get(sku)
                .stream()
                .filter(productPrice -> productPrice.getUnit().equals(unit))
                .map(ProductPrice::getPrice)
                .collect(Collectors.toList());
    }
}
