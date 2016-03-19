package me.sujianxin.spring.util;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/9
 * <p>Time: 9:53
 * <p>Version: 1.0
 */
public class MapUtil {
    private static Map<String, Object> deleteMap = new HashMap<>(2);
    private static Map<String, Object> deleteForbiddenMap = new HashMap<>(2);
    private static Map<String, Object> saveMap = new HashMap<>(2);
    private static Map<String, Object> updateSuccessMap = new HashMap<>(2);
    private static Map<String, Object> updateFailMap = new HashMap<>(2);
    private static Map<String, Object> forbiddenOperationMap = new HashMap<>(2);
    private static Map<String, Object> ableMap = new HashMap<>(2);
    private static Map<String, Object> userPasswordResetRequstFailMap = new HashMap<>(2);
    private static Map<String, Object> userPasswordResetRequstSuccessMap = new HashMap<>(2);
    private static Map<String, Object> imageDeleteSuccessMap = new HashMap<>(2);
    private static Map<String, Object> imageDeleteFailMap = new HashMap<>(2);

    public static Map<String, Object> getDeleteMap() {
        if (deleteMap.isEmpty()) {
            deleteMap.put("success", true);
            deleteMap.put("msg", "删除成功");
        }
        return deleteMap;
    }

    public static Map<String, Object> getDeleteForbiddenMap() {
        if (deleteForbiddenMap.isEmpty()) {
            deleteForbiddenMap.put("success", false);
            deleteForbiddenMap.put("msg", "已处理投稿不允许删除");
        }
        return deleteForbiddenMap;
    }

    public static Map<String, Object> getSaveMap() {
        if (saveMap.isEmpty()) {
            saveMap.put("success", true);
            saveMap.put("msg", "保存成功");
        }
        return saveMap;
    }

    public static Map<String, Object> getUpdateSuccessMap() {
        if (updateSuccessMap.isEmpty()) {
            updateSuccessMap.put("success", true);
            updateSuccessMap.put("msg", "更新成功");
        }
        return updateSuccessMap;
    }

    public static Map<String, Object> getUpdateFailMap() {
        if (updateFailMap.isEmpty()) {
            updateFailMap.put("success", true);
            updateFailMap.put("msg", "更新成功");
        }
        return updateFailMap;
    }

    public static Map<String, Object> getForbiddenOperationMap() {
        if (forbiddenOperationMap.isEmpty()) {
            forbiddenOperationMap.put("success", false);
            forbiddenOperationMap.put("msg", "非法操作");
        }
        return forbiddenOperationMap;
    }

    public static Map<String, Object> getAbleMap() {
        if (ableMap.isEmpty()) {
            ableMap.put("success", false);
            ableMap.put("msg", "操作成功");
        }
        return ableMap;
    }

    public static Map<String, Object> getUserPasswordResetRequstFailMap() {
        if (userPasswordResetRequstFailMap.isEmpty()) {
            userPasswordResetRequstFailMap.put("success", false);
            userPasswordResetRequstFailMap.put("msg", "账户不存在，请确认");
        }
        return userPasswordResetRequstFailMap;
    }

    public static Map<String, Object> getUserPasswordResetRequstSuccessMap() {
        if (userPasswordResetRequstSuccessMap.isEmpty()) {
            userPasswordResetRequstSuccessMap.put("success", true);
            userPasswordResetRequstSuccessMap.put("msg", "重置密码邮件已发送到您的邮箱，请查收");
        }
        return userPasswordResetRequstSuccessMap;
    }

    public static Map<String, Object> getImageDeleteSuccessMap() {
        if (imageDeleteSuccessMap.isEmpty()) {
            imageDeleteSuccessMap.put("success", true);
            imageDeleteSuccessMap.put("msg", "图片删除成功");
        }
        return imageDeleteSuccessMap;
    }

    public static Map<String, Object> getImageDeleteFailMap() {
        if (imageDeleteFailMap.isEmpty()) {
            imageDeleteFailMap.put("success", false);
            imageDeleteFailMap.put("msg", "文件不存在，图片删除失败");
        }
        return imageDeleteFailMap;
    }
}
