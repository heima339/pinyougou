package cn.itcast.core.service;

import cn.itcast.common.utils.DateUtils;
import cn.itcast.core.dao.order.OrderDao;
import cn.itcast.core.dao.seller.SellerDao;
import cn.itcast.core.pojo.seller.Seller;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SalesServiceImpl implements SalesService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private SellerDao sellerDao;

    /**
     * 根据指定日期查询商家销售额
     *
     * @param date yyyy-MM-dd
     * @return
     */
    @Override
    public List<Map<String, Object>> getSellerSales(String date) {
        //根据string格式日期,获得当天的起始结束时间点String数组
        String[] pointStr = getDayStartEndPoints(date);
        //根据日期查询数据库,按照商家分组List<Map<sellerID,sales>>
        List<Map<String, Object>> list = orderDao.findDaySalesGroupBySellerId(pointStr[0], pointStr[1]);
        //判断集合不为空,遍历集合,根据商家id去查询商家店铺名称
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                //获取商家id和销售数据map集合
                Map<String, Object> map = list.get(i);
                //获得商家id
                String sellerId = String.valueOf(map.get("id"));
                //创建新的map用来存储店铺名称和销售额
                Map<String, Object> newMap = new HashMap<>();
                //获得集合中的key,查询对应的seller对象
                Seller seller = sellerDao.selectByPrimaryKey(sellerId);
                //把店铺名称和销售额存储到新的map中
                newMap.put("name", seller.getNickName());
                newMap.put("value", map.get("sales"));
                //修改list对应索引位置的商家数据
                list.set(i, newMap);
            }
        }
        return list;
    }

    /**
     * 根据string格式日期,获得当天的起始结束时间点String数组
     *
     * @param date
     * @return
     */
    private String[] getDayStartEndPoints(String date) {
        //先将前台传递过来的String格式的date转换成Date对象
        Date day = formatStrToDate(date);
        //获得每天的开始时间和结束时间点
        return DateUtils.getDayStartAndEndTimePointStr(day);
    }

    /**
     * 商家查询指定日期销售数据
     *
     * @param date
     * @param name
     * @return
     */
    @Override
    public Map getSellerSales(String date, String name) {
        //获得当天的起始时间点数组
        String[] points = getDayStartEndPoints(date);
        Map map = orderDao.findDaySalesBySellerId(points[0], points[1], name);
        return map;
    }

    /**
     * 商家查询指定时间段日销售数据
     *
     * @param start
     * @param end
     * @param name
     * @return
     */
    @Override
    public Map getPeriodSales(String start, String end, String name) {
        //格式化日期对象
        Date startDay = formatStrToDate(start);
        Date endDay = formatStrToDate(end);
        //获得开始结束天数的list集合
        List<Date> periodOfTime = DateUtils.getPeriodOfTime(startDay, endDay);
        HashMap<Object, Object> map = new HashMap<>();
        List<String> days = new ArrayList<>();
        List<Double> money = new ArrayList<>();
        //判断集合不为空,进行遍历
        if (periodOfTime != null && periodOfTime.size() > 0) {
            for (Date date : periodOfTime) {
                days.add(DateUtils.formatDateToStr(date));
                money.add(getDaySales(date));
            }
        }
        map.put("days",days);
        map.put("moneyList",money);
        return map;
    }

    /**
     * 字符串转时间对象
     *
     * @param date
     * @return
     */
    private Date formatStrToDate(String date) {
        if (!"".equals(date)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return format.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 查询7日销售数据
     * key string     value 数组
     * 日期			sevenDays[1,2,3,4,5,6,7]
     * 数据			sevenDataSales[100,343,4323,4352,234,67,564]
     */
    @Override
    public Map getSevenSales() {
        Map<Object, Object> map = new HashMap<>();
        // 根据当前日期获取前第7天开始的日期集合
        List<Date> sevenDays = getDates();
        List<String> sevenDaysStr = new ArrayList<>();
        List<String> listSales = new ArrayList<>();

        if (sevenDays != null && sevenDays.size() > 0) {
            //格式化日期集合,统一格式为:2019-04-13
            for (int i = 0; i < sevenDays.size(); i++) {
                sevenDaysStr.add(DateUtils.formatDateToStr(sevenDays.get(i)));
            }

            //遍历7天日期

            for (Date day : sevenDays) {
                listSales.add(String.valueOf(getDaySales(day)));
            }
        }
        map.put("sevenDays", sevenDaysStr);
        map.put("sevenDataSales", listSales);
        return map;
    }

    /**
     * 根据指定日期查询当日销售
     * Sat Apr 13 11:52:07 CST 2019
     * [2019-04-13 00:00:00, 2019-04-13 23:59:59]
     *
     * @param day
     * @return
     */
    private Double getDaySales(Date day) {
        //获得每天的开始时间和结束时间点
        String[] pointStr = DateUtils.getDayStartAndEndTimePointStr(day);
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//        Double money = null;
//        try {
//            Date t1 = format.parse(pointStr[0]);
//            Date t2 = format.parse(pointStr[0]);
        Double byDay = orderDao.countSalesByDay(pointStr[0], pointStr[1]);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        return byDay;
    }

    /**
     * 根据当前日期获取前第7天开始的日期集合,共8天
     *
     * @return
     */
    private List<Date> getDates() {
        //根据当前时间,获得前第7天的日期
        Date today = new Date();
        Date pre7Day = DateUtils.dateAddOrSubtract(new Date(), -7);
        //根据前第7天日期,含今日期,获得时间段list<date>集合
        return DateUtils.getPeriodOfTime(pre7Day, today);
    }

    public static void main(String[] args) throws ParseException {
        //开始结束,时间点[2019-04-13 00:00:00, 2019-04-13 23:59:59]
        String[] timePointStr = DateUtils.getDayStartAndEndTimePointStr(new Date());

        //格式化日期2019-04-13
        String date = DateUtils.formatDateToStr(new Date());

        //截取时间  Sat Apr 06 11:07:39 CST 2019
        Date date1 = DateUtils.dateAddOrSubtract(new Date(), -7);


        //获得时间段每日的list集合
        //Mon Apr 08 11:11:55 CST 2019
        //Tue Apr 09 11:11:55 CST 2019
        //Wed Apr 10 11:11:55 CST 2019
        //Thu Apr 11 11:11:55 CST 2019
        //Fri Apr 12 11:11:55 CST 2019
        //Sat Apr 13 11:11:55 CST 2019
        //Sun Apr 14 11:11:55 CST 2019
        List<Date> time = DateUtils.getPeriodOfTime(new Date(), date1);

        //根据当前时间,获得前第7天的日期
        Date today = new Date();
//        Calendar c = Calendar.getInstance();
//        c.setTime(today);
//        c.set(Calendar.MINUTE,0);
//        c.set(Calendar.HOUR,0);
//        c.set(Calendar.SECOND,0);
//        Date today1 = c.getTime();
//        System.out.println();

//        String str = "2019-04-13 00:00:00";
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        Date date444 = format.parse(str);
//        System.out.println(date444);
        Date pre7Day = DateUtils.dateAddOrSubtract(today, -7);
        //根据前第7天日期,和今日期,获得时间段list<date>集合
        List<Date> periodOfTime = DateUtils.getPeriodOfTime(pre7Day, today);
        for (Date date2 : periodOfTime) {
            System.out.println(date2);
            String[] str = DateUtils.getDayStartAndEndTimePointStr(date2);
            System.out.println(Arrays.toString(str));
        }


    }
}
