package de.bringmeister.dao.product;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import de.bringmeister.domain.Product;
import de.bringmeister.exceptions.AppInitException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by petermihal2 on 11/02/2018.
 */
@Component
public class ProductDaoFileXml implements IProductDao {

    private Map<String, Product> productsMap = null;

    @PostConstruct
    public void initProductsMap() {

        ClassLoader classLoader = getClass().getClassLoader();
        Path filePath = null;
        try {
            filePath = Paths.get(classLoader.getResource("products/products.xml").toURI());
        } catch (URISyntaxException e) {
            throw new AppInitException(e);
        }

        try {
            String xml = StringUtils.toEncodedString(Files.readAllBytes(filePath), StandardCharsets.UTF_8);

            JacksonXmlModule xmlModule = new JacksonXmlModule();
            xmlModule.setDefaultUseWrapper(false);
            ObjectMapper objectMapper = new XmlMapper();
            objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

            List<Product> products = objectMapper.readValue(xml, Products.class).getProducts();

            productsMap = products.stream().collect(Collectors.toMap(Product::getId, Function.identity()));

        } catch (IOException e) {
            throw new AppInitException(e);
        }
    }

    @Override
    public List<Product> getProducts(){

        if(productsMap == null) {
            initProductsMap();
        }

        return new ArrayList<>(productsMap.values());
    }


    @Override
    public Product findOne(String id){

        if(productsMap == null) {
            initProductsMap();
        }

        if(!productsMap.containsKey(id)){
            return null;
        }

        return productsMap.get(id);
    }

}
