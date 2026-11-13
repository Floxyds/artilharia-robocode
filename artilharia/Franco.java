package artilharia;
import robocode.*;
import robocode.util.Utils;

public class Franco extends TeamRobot {

    int sentido = 1;            
    boolean noCanto = false;    

    public void run() {
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);

        
        int tentativas = 0;
        while (Math.hypot(20 - getX(), 20 - getY()) > 5 && tentativas < 10) {
            irParaCanto(20, 20);
            tentativas++;
        }
        noCanto = true;

       
        turnGunRight(Utils.normalRelativeAngleDegrees(0 - getGunHeading()));
        turnRadarRight(Utils.normalRelativeAngleDegrees(0 - getRadarHeading()));

        while (true) {
            turnRadarRight(90 * sentido);
            sentido = -sentido;
        }
    }

    public void irParaCanto(double destX, double destY) {
        double dx = destX - getX();
        double dy = destY - getY();
        double anguloAlvo = Math.toDegrees(Math.atan2(dx, dy));
        double giro = Utils.normalRelativeAngleDegrees(anguloAlvo - getHeading());
        turnRight(giro);
        ahead(Math.hypot(dx, dy));
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        if (isTeammate(e.getName())) return;

        double anguloAbsoluto = getHeading() + e.getBearing();
        double giroCanhao = Utils.normalRelativeAngleDegrees(anguloAbsoluto - getGunHeading());

        setTurnGunRight(giroCanhao);
        if (Math.abs(giroCanhao) < 10 && getGunHeat() == 0) {
            setFire(2);
        }
    }

    public void onHitRobot(HitRobotEvent e) {
        if (!noCanto) {     
            back(40);
            turnRight(60);
        }
    }
}