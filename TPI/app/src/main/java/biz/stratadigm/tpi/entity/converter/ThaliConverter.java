package biz.stratadigm.tpi.entity.converter;

import java.util.ArrayList;
import java.util.List;

import biz.stratadigm.tpi.entity.dto.ThaliDTO;
import biz.stratadigm.tpi.entity.vo.ThaliVO;

public class ThaliConverter {

    public static ThaliVO toThaliVO(ThaliDTO dto) {
        ThaliVO thaliVO = new ThaliVO(dto.getName(), dto.getRegion(), dto.getImage(), dto.getPrice());
        //ThaliVO thaliVO = new ThaliVO(dto.getName(), 0, 0f, 0f);
        return thaliVO;
    }

    public static List<ThaliVO> toThaliVOs(List<ThaliDTO> dtos) {
        List<ThaliVO> vos = new ArrayList<>(dtos.size());
        for (ThaliDTO dto : dtos) {
            vos.add(toThaliVO(dto));
        }
        return vos;
    }
}
