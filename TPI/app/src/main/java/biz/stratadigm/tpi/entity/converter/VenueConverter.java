package biz.stratadigm.tpi.entity.converter;

import java.util.ArrayList;
import java.util.List;

import biz.stratadigm.tpi.entity.dto.VenueDTO;
import biz.stratadigm.tpi.entity.vo.VenueVO;

public class VenueConverter {

    public static VenueVO toVenueVO(VenueDTO dto) {
        VenueVO venueVO = new VenueVO(dto.getName(), dto.getThalis().size(), dto.getLocation().getLat(), dto.getLocation().getLng());
        //VenueVO venueVO = new VenueVO(dto.getName(), 0, 0f, 0f);
        return venueVO;
    }

    public static List<VenueVO> toVenueVOs(List<VenueDTO> dtos) {
        List<VenueVO> vos = new ArrayList<>(dtos.size());
        for (VenueDTO dto : dtos) {
            vos.add(toVenueVO(dto));
        }
        return vos;
    }
}
