package liu.chun.dong.dao.mapper;

import liu.chun.dong.dao.entity.SegmentInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * @author wyliuchundong
 * @version 1.0.0
 * @date 2015/3/21 16:47
 * @comment SegmentInfoMapper
 */
public interface SegmentInfoMapper {
    public List<SegmentInfoEntity> getSegmentInfo();

    /**
     * 根据start_link或者end_link查找segmentInfo
     * @param map
     * @return
     */
    public List<SegmentInfoEntity> getSegmentInfoByLink(Map<String, Integer> map);
}
