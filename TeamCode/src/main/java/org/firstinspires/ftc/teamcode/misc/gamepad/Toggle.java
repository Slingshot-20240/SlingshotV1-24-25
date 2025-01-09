package org.firstinspires.ftc.teamcode.misc.gamepad;

public class Toggle {
    private boolean value;

    // If the button is being held, the toggle is locked from changing until the button is let go
    private boolean buttonLock = false;

    // If toggle changed last update
    // this will just make it so one press = true, and pressing it again isn't considered false (like a toggle)
    private boolean changed = false;

    // If turned true on last toggle
    private boolean risingEdge = false;

    private boolean fallingEdge = false;

    public Toggle(boolean initialValue) { this.value = initialValue; }

    public void update(boolean buttonPressed) {
        // Returns true if value has been toggled, false otherwise
        if (buttonPressed) {
            if (!buttonLock) {
                value = !value;
                risingEdge = value; // low state to high state
                fallingEdge = !value; // high state to low state
                buttonLock = true;
                changed = true;
                return;
            }
        } else { buttonLock = false; }
        changed = false;
        risingEdge = false;
        fallingEdge = false;
    }

    public void set(boolean value) { this.value = value; }
    public boolean value() { return value; }
    public boolean changed() { return changed; }
    public boolean risingEdge() { return risingEdge; }
    public boolean fallingEdge() { return fallingEdge; }
    public boolean locked() { return buttonLock; }
    public void setLocked(boolean locked) { this.buttonLock = locked; }
}
