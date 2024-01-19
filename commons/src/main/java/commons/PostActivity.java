package commons;

import java.util.Arrays;
import java.util.Objects;

public class PostActivity{

    Activity activity;
    private byte[] pictureBuffer;

    public PostActivity(Activity activity, byte[] pictureBuffer) {
        this.activity = activity;
        this.pictureBuffer = pictureBuffer;
    }

    public PostActivity() {}

    public byte[] getPictureBuffer() {
        return pictureBuffer;
    }

    public Activity getActivity() {
        return activity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostActivity)) return false;
        PostActivity that = (PostActivity) o;
        return Objects.equals(getActivity(), that.getActivity())
                && Arrays.equals(getPictureBuffer(), that.getPictureBuffer());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getActivity());
        result = 31 * result + Arrays.hashCode(getPictureBuffer());
        return result;
    }
}
