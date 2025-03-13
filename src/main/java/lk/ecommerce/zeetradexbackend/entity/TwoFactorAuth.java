package lk.ecommerce.zeetradexbackend.entity;

import jakarta.persistence.Entity;
import lk.ecommerce.zeetradexbackend.enums.VerificationType;


public class TwoFactorAuth {
    private boolean isEnabled = false;
    private VerificationType sendTo;

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public VerificationType getSendTo() {
        return sendTo;
    }

    public void setSendTo(VerificationType sendTo) {
        this.sendTo = sendTo;
    }
}
