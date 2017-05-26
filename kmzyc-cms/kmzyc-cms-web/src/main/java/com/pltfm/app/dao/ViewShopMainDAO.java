package com.pltfm.app.dao;

import com.pltfm.app.vobject.ViewShopMain;

import java.sql.SQLException;
import java.util.List;

public interface ViewShopMainDAO {
    public List queryShopMianList(ViewShopMain viewShopMain) throws SQLException;

}