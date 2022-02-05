public class PeakFitnessBuilderFactory {
  private DocumentBuilderFactory documentBuilderFactory() {
    // hàm làm nhiệm vụ phân tích cú pháp XML
    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

    try {
      documentBuilderFactory.setFeature("http://xml.org/sax/features/external-general-entities", true);
      // Các tùy chọn load thực thể bên ngoài được bật .(mặc định là true ngay cả khi ko đặt)
      documentBuilderFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", true);
    } catch (ParserConfigurationException e) {
      throw new RuntimeException(e);
    }

    return documentBuilderFactory;
  }
}
/*
 *documentBuilderFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
 *documentBuilderFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
 *documentBuilderFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);

*/