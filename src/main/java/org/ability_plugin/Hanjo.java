package org.ability_plugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
public class Arrow extends JavaPlugin implements CommandExecutor {
    JavaPlugin plugin;

    public Arrow(JavaPlugin plugin) {
        this.plugin = plugin;   //메타데이터를 위해 플러그인 객체를 갖고 있어야 함
    }

    private Vector getDir(double yaw, double dirY, double angleAdd) //바라보는 방향을 벡터로 가져오는 함수
    {
        double dirX = Math.cos(Math.toRadians(yaw + 90 + angleAdd));
        double dirZ = Math.sin(Math.toRadians(yaw + 90 + angleAdd));
        return new Vector(dirX, dirY, dirZ);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {  //명령어 실행 시
        if(sender instanceof Player) {  //명령어 사용자가 플레이어인 경우
            Player player = (Player)sender; //명령어 사용자 객체를 플레이어 객체로 변환할 수 있음
            Random random = new Random();
            for(int i = 0; i < 15; i++) {
                //화살을 수평으로 15개 발사
                Arrow pr = player.launchProjectile(Arrow.class, getDir(player.getLocation().getYaw(), player.getLocation().getDirection().getY(), random.nextDouble() * 45 - 22.5).multiply(3));
                pr.setMetadata("shotgun", new FixedMetadataValue(plugin, true));    //생성된 화살에 명령어로 생성되었다는 것을 표시
                pr.setGravity(false);   //중력의 영향을 받지 않게 설정하여 낙차가 없게 함
                pr.setCritical(true);   //화살의 이펙트를 위해 크리티컬 판정을 설정
                pr.setDamage(10);       //화살 피격 시 10대미지를 받도록 설정 (일반 플레이어 체력의 반)
            }
            return true;    //true값을 반환하면 명령어가 성공한 것으로 간주
        }
        else if(sender instanceof ConsoleCommandSender) {   //명령어 사용자가 콘솔인 경우
            sender.sendMessage("콘솔에서는 이 명령어를 실행할 수 없습니다.");
            return false;   //false값을 반환하면 명령어가 실패한 것으로 간주
        }
        return false;   //false값을 반환하면 명령어가 실패한 것으로 간주
    }
}
