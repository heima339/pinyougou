package cn.itcast.core.service;

import cn.itcast.common.utils.DateUtils;
import cn.itcast.core.dao.order.OrderDao;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.*;

@Service
public class SalesServiceImpl implements SalesService {
    @Autowired
    private OrderDao orderDao;

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
