/*
 * 删除微商业务 package com.pltfm.app.service.impl;
 * 
 * import java.math.BigDecimal; import java.sql.SQLException; import java.util.List; import
 * java.util.Map;
 * 
 * import javax.annotation.Resource;
 * 
 * import org.springframework.stereotype.Service; import
 * org.springframework.transaction.annotation.Propagation; import
 * org.springframework.transaction.annotation.Transactional;
 * 
 * import com.pltfm.app.dao.SpreadEffectDAO; import com.pltfm.app.service.SpreadEffectService;
 * import com.pltfm.app.vobject.SpreadEffect; import com.kmzyc.commons.exception.ServiceException;
 * 
 * @SuppressWarnings("unchecked")
 * 
 * @Service("spreadEffectService") public class SpreadEffectServiceImpl implements
 * SpreadEffectService {
 * 
 * @Resource private SpreadEffectDAO spreadEffectDAO;
 * 
 * @Override
 * 
 * @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor =
 * ServiceException.class) public BigDecimal saveSpreadResultRecode(SpreadEffect vo) throws
 * SQLException { return spreadEffectDAO.insertSelective(vo); }
 * 
 * @Override
 * 
 * @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor =
 * ServiceException.class) public int updateSpreadResultRecode(SpreadEffect vo) throws SQLException
 * { return spreadEffectDAO.updateByPrimaryKeySelective(vo); }
 * 
 * @Override public List<SpreadEffect> querySpreadResultRecode(Map map) throws SQLException { return
 * spreadEffectDAO.querySpreadEffectList(map); }
 * 
 * @Override public List<SpreadEffect> querySpreadDetail(Map map) throws SQLException { return
 * spreadEffectDAO.querySpreadEffectDetailList(map); } }
 */
