import com.deltateam.deltalib.API.animation.keyframes.animator.builders.KeyframeSequenceBuilder;
import com.deltateam.deltalib.API.animation.keyframes.keyframes.BasicKeyframe;
import com.deltateam.deltalib.API.animation.keyframes.keyframes.Keyframe;
import com.deltateam.deltalib.util.animation.KeyframeGroup;
import net.minecraft.world.phys.Vec3;

public class AnimationBuilderTest {
	public static void main(String[] args) {
		KeyframeSequenceBuilder sequenceBuilder = new KeyframeSequenceBuilder();
		sequenceBuilder.addPosition(10, new Vec3(0, 0, 0));
		sequenceBuilder.addPosition(20, new Vec3(0, 10, 0));
		sequenceBuilder.addPosition(40, new Vec3(0, 30, 0));
		sequenceBuilder.addPosition(30, new Vec3(0, 20, 0));
		
		sequenceBuilder.addRotation(0, new Vec3(0, 0, 0), false);
		sequenceBuilder.addRotation(40, new Vec3(0, Math.toRadians(45), 0), false);
		KeyframeGroup output = sequenceBuilder.build();
		
		System.out.println("Position");
		Keyframe current = output.position;
		while (current != null) {
			System.out.print(current.keyframeDuration);
			if (current instanceof BasicKeyframe basicKeyframe) {
				System.out.print(", ");
				System.out.println(basicKeyframe.getTarget());
			} else {
				System.out.println();
			}
			current = current.nextframe;
		}
		
		System.out.println("Rotation");
		current = output.rotation;
		while (current != null) {
			System.out.print(current.keyframeDuration);
			if (current instanceof BasicKeyframe basicKeyframe) {
				System.out.print(", ");
				System.out.println(basicKeyframe.getTarget());
			} else {
				System.out.println();
			}
			current = current.nextframe;
		}
	}
}
