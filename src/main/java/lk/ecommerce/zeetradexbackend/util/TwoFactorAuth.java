package lk.ecommerce.zeetradexbackend.util;

import lk.ecommerce.zeetradexbackend.enums.VerificationType;
import lombok.Data;

@Data
public class TwoFactorAuth {

    private boolean isEnabled = false;

    private VerificationType sendTo;

}
