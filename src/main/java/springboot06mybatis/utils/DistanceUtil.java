package springboot06mybatis.utils;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalCoordinates;

/**
 * ClassName:    DistanceUtil
 * Package:    springboot06mybatis.utils
 * 输入：两个地理坐标
 * 输出：Double类型的距离
 * longitude：经度
 * latitude：纬度
 * Datetime:    2020/4/12   15:50
 * Author:   hewson.chen@foxmail.com
 */
public class DistanceUtil {
    public static double getDistance(double longitudeFrom, double latitudeFrom, double longitudeTo, double latitudeTo) {
        GlobalCoordinates source = new GlobalCoordinates(latitudeFrom, longitudeFrom);
        GlobalCoordinates target = new GlobalCoordinates(latitudeTo, longitudeTo);

        return new GeodeticCalculator().calculateGeodeticCurve(Ellipsoid.Sphere, source, target).getEllipsoidalDistance();
    }
}
