import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.opencv.opencv_core.Mat;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import static org.bytedeco.opencv.global.opencv_core.CV_8UC4;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;

public class Sample {
    public static void main(String[] args) throws IOException {
        // URLから画像を取得
        URL url = new URL("https://raw.githubusercontent.com/opencv/opencv/4.x/samples/data/fruits.jpg");
        BufferedImage bufferedImage = ImageIO.read(url);

        // 各画素のARGB色情報を抽出
        // intは4byteなので、各byteがそれぞれARGBの各要素に対応している
        int[] argbArray = bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null, 0, bufferedImage.getWidth());

        // int配列をbyte配列に変換する
        // その際、Mat型で読み込めるようにARGBの並びをBGRAに変換する
        byte[] bgraArray = new byte[argbArray.length * 4];
        for (int i = 0; i < argbArray.length; i++) {
            bgraArray[i * 4 + 0] = (byte) ((argbArray[i] >> 0) & 0xFF); // B
            bgraArray[i * 4 + 1] = (byte) ((argbArray[i] >> 8) & 0xFF); // G
            bgraArray[i * 4 + 2] = (byte) ((argbArray[i] >> 16) & 0xFF); // R
            bgraArray[i * 4 + 3] = (byte) ((argbArray[i] >> 24) & 0xFF); // A
        }

        // Mat型として読み込み
        Mat image = new Mat(bufferedImage.getHeight(), bufferedImage.getWidth(), CV_8UC4, new BytePointer(bgraArray));
        imwrite("result.jpg", image);
    }
}
