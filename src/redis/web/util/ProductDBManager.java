package redis.web.util;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;

import com.google.gson.Gson;

public class ProductDBManager extends RedisDBManager {

	private ProductDBManager() {

	}

	public static ProductDBManager singleton = new ProductDBManager();

	public boolean commisionProduct(Map<String, String> productAttributes) {
		Jedis jedis = this.getConnection();
		String productCreationResult = jedis.hmset(productAttributes.get("name"), productAttributes);
		if (productCreationResult.toLowerCase().equals("ok")) {
			this.returnConnection(jedis);
			return true;
		} else {
			this.returnConnection(jedis);
			return false;
		}
	}

	public boolean enterTagEntries(String name, String string) {
		Jedis jedis = this.getConnection();
		String[] tags = string.split(",");
		boolean boolResult = false;

		List<String> tagList = new ArrayList<String>();
		for (String tag : tags) {
			String[] tagAndRating = tag.split("@");
			tagList.add(tagAndRating[0]);
		}

		for (String tag : tagList) {
			long result = jedis.zadd(tag.toLowerCase(), 0, name);
			if (result == 0) {
				break;
			} else {
				boolResult = true;
			}
		}
		this.returnConnection(jedis);
		return boolResult;
	}

	public String getProductInfo(String name) {
		Jedis jedis = this.getConnection();
		Map<String, String> map = jedis.hgetAll(name);
		map.replace("name", map.get("name").split("@")[0]);
		Gson gson = new Gson();
        String json = gson.toJson(map);
		this.returnConnection(jedis);
		return json;
	}

	public String getTagValues(String tagName) {
		Jedis jedis = this.getConnection();
		Set<String> sortedTagList = jedis.zrange(tagName.toLowerCase(), 0, 10000);
		Set<String> values = new HashSet<String>();
		for (String tagname : sortedTagList) {
			values.add(tagname.split("@")[0]);
		}
		Gson gson = new Gson();
        String json = gson.toJson(values);
		this.returnConnection(jedis);
		return json;
	}

	public boolean keyExist(String keyName) {
		Jedis jedis = this.getConnection();
		boolean result = jedis.exists(keyName);
		this.returnConnection(jedis);
		return result;
	}

	public int getPurchaseToday(String productName) {
		Jedis jedis = this.getConnection();
		if (jedis.get(productName + "@purchase:" + getDate()) != null) {
			BitSet users = BitSet.valueOf(jedis.get(productName + "@purchase:" + getDate()).getBytes());
			this.returnConnection(jedis);
			return users.cardinality();
		} else {
			this.returnConnection(jedis);
			return 0;
		}
	}

	public Map<String, Integer> getProductTags(String productname) {
		Jedis jedis = this.getConnection();
		String producttags = jedis.hget(productname, "tags");
		Map<String, Integer> map = new HashMap<String, Integer>();
		String[] tagAndweights = producttags.split(",");
		for (String tagAndWeight : tagAndweights) {
			map.put(tagAndWeight.split("@")[0], new Integer(tagAndWeight.split("@")[1]));
		}
		this.returnConnection(jedis);
		return map;
	}
	
	public String getProducts(String args) {
		Jedis jedis = this.getConnection();
		Set<String> products = jedis.keys("*@products");
		List<String> products2 = new ArrayList<String>();
		for (String product : products){
			products2.add(product.split("@")[0]);
		}
		Gson gson = new Gson();
        String json = gson.toJson(products2);
		this.returnConnection(jedis);
		return json;
	}
	
	public String getTags(String args) {
		Jedis jedis = this.getConnection();
		Set<String> products = jedis.keys("*@products");
		Set<String> tags = new HashSet<String>();
		for (String product : products){
			String[] producttags = jedis.hget(product, "tags").split(",");
			for(String tag : producttags){
				tags.add(tag.split("@")[0]);
			}
		}
		Gson gson = new Gson();
        String json = gson.toJson(tags);
		this.returnConnection(jedis);
		return json;
	}

}
