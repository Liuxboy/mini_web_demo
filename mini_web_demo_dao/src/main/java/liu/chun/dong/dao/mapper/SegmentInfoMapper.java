package liu.chun.dong.dao.mapper;

import liu.chun.dong.dao.entity.SegmentInfoEntity;

import java.util.List;

/**
 * @author wyliuchundong
 * @version 1.0.0
 * @date 2015/3/21 16:47
 * @comment SegmentInfoMapper
 */
public interface SegmentInfoMapper {
    List<SegmentInfoEntity> getSegmentInfo();
}
