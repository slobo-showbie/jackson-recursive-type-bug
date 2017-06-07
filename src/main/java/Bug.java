import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.type.*;
import com.fasterxml.jackson.databind.util.*;
import com.fasterxml.jackson.annotation.*;

import java.lang.reflect.*;
import java.util.*;

public class Bug {
	public static void main(String[] args) throws Exception {
		{
			TypeFactory tf = TypeFactory.defaultInstance();

			JavaType baseJT;
			JavaType subJT;
			if (args.length == 0 || args[0].equals("base")) {
				// base constructed first
				baseJT = tf.constructType(Base.class);
				subJT = tf.constructType(Sub.class);
			}
			else {
				// sub constructed first
				subJT = tf.constructType(Sub.class);
				baseJT = tf.constructType(Base.class);
			}

			System.out.println("baseJT = " + baseJT);
			System.out.println("subJT  = " + subJT);
			System.out.println("subJT.getSuperClass()  = " + subJT.getSuperClass());

			Sub sub = new Sub();

			ObjectMapper om = new ObjectMapper();
			System.out.println("json(sub) = " + om
					.writerFor(sub.getClass())
					.writeValueAsString(sub));
		}
	}

	static interface IFace<T> {
	}

	static class Base implements IFace<Sub> {
		@JsonProperty int base = 1;
	}

	static class Sub extends Base {
		@JsonProperty int sub = 2;
	}
}
