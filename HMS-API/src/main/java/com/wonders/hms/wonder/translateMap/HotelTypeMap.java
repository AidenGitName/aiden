package com.wonders.hms.wonder.translateMap;

import com.wonders.hms.wonder.type.HotelTypeKind;

import java.util.HashMap;
import java.util.Map;

public class HotelTypeMap {
    public static final Map<String, HotelTypeKind> koNameToTypeKind;
    static {
        koNameToTypeKind = new HashMap<String, HotelTypeKind>();
        koNameToTypeKind.put("호텔", HotelTypeKind.HOTEL);
        koNameToTypeKind.put("리조트", HotelTypeKind.RESORT);
        koNameToTypeKind.put("펜션", HotelTypeKind.PENSION);
        koNameToTypeKind.put("모텔", HotelTypeKind.MOTEL);
        koNameToTypeKind.put("게스트하우스", HotelTypeKind.GUEST_HOUSE);
        koNameToTypeKind.put("레지던스", HotelTypeKind.RESIDENCE);
        koNameToTypeKind.put("카라반", HotelTypeKind.CARAVAN);
    }

    public static final Map<HotelTypeKind, String> typeKindToEnName;
    static {
        typeKindToEnName = new HashMap<HotelTypeKind, String>();
        typeKindToEnName.put(HotelTypeKind.HOTEL, "Hotel");
        typeKindToEnName.put(HotelTypeKind.RESORT, "Hotel resort");
        typeKindToEnName.put(HotelTypeKind.PENSION, "Pension");
        typeKindToEnName.put(HotelTypeKind.MOTEL, "Motel");
        typeKindToEnName.put(HotelTypeKind.GUEST_HOUSE, "Guest House");
        typeKindToEnName.put(HotelTypeKind.RESIDENCE, "Residence");
        typeKindToEnName.put(HotelTypeKind.CARAVAN, "Caravan Park");
    }
}
