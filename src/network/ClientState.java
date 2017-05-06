package network;

import java.io.Serializable;

/**
 * Created by arons on 2017. 05. 04..
 */
public class ClientState implements Serializable {
    private boolean rightPressed;
    private boolean leftPressed;
    private boolean spacePressed;

    public ClientState(boolean rightPressed, boolean leftPressed, boolean spacePressed) {
        this.rightPressed = rightPressed;
        this.leftPressed = leftPressed;
        this.spacePressed = spacePressed;
    }

    public ClientState() {
        this.rightPressed = false;
        this.leftPressed = false;
        this.spacePressed = false;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public boolean isSpacePressed() {
        return spacePressed;
    }

    public void setSpacePressed(boolean spacePressed) {
        this.spacePressed = spacePressed;
    }
}
