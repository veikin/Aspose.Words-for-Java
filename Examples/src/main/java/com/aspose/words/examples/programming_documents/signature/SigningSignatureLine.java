package com.aspose.words.examples.programming_documents.signature;

import com.aspose.words.examples.Utils;
import com.aspose.words.*;

import java.io.File;
import java.nio.file.Files;

/**
 * Created by Home on 8/10/2017.
 */
public class SigningSignatureLine {


    public static void main(String[] args) throws Exception {
        // The path to the documents directory.
        String dataDir = Utils.getDataDir(SigningSignatureLine.class);
        File certificate= new File(dataDir + "temp.pfx");
        if (!certificate.exists())
        {
            System.out.println("Certificate file does not exist.");
            return;
        }
        SimpleDocumentSigning(dataDir);
        SigningEncryptedDocument(dataDir);
        CreatingAndSigningNewSignatureLine(dataDir);
        SigningExistingSignatureLine(dataDir);

    }

    public static void SimpleDocumentSigning(String dataDir) throws Exception
    {
        // ExStart:SimpleDocumentSigning
        CertificateHolder certHolder = CertificateHolder.create(dataDir + "temp.pfx", "password");
        DigitalSignatureUtil.sign(dataDir + "Document.Signed.docx", dataDir + "Document.Signed_out.docx", certHolder);

        // ExEnd:SimpleDocumentSigning
        System.out.println("\nDocument is signed successfully.\nFile saved at " + dataDir + "Document.Signed_out.docx");
    }

    public static void SigningEncryptedDocument(String dataDir) throws Exception
    {
        // ExStart:SigningEncryptedDocument

        SignOptions signOptions = new SignOptions();
        signOptions.setDecryptionPassword("decryptionPassword");

        CertificateHolder certHolder = CertificateHolder.create(dataDir + "temp.pfx", "password");
        DigitalSignatureUtil.sign(dataDir + "Document.Signed.docx", dataDir + "Document.EncryptedDocument_out.docx", certHolder, signOptions);
        // ExEnd:SigningEncryptedDocument
        System.out.println("\nDocument is signed with successfully.\nFile saved at " + dataDir + "Document.EncryptedDocument_out.docx");

    }

    public static void CreatingAndSigningNewSignatureLine(String dataDir) throws Exception
    {
        // ExStart:CreatingAndSigningNewSignatureLine
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);
        SignatureLine signatureLine = builder.insertSignatureLine(new SignatureLineOptions()).getSignatureLine();
        doc.save(dataDir + "Document.NewSignatureLine.docx");

        SignOptions signOptions = new SignOptions();
        signOptions.setSignatureLineId(signatureLine.getId());
        java.nio.file.Path path = java.nio.file.Paths.get(dataDir + "SignatureImage.emf");
        signOptions.setSignatureLineImage(Files.readAllBytes(path));


        CertificateHolder certHolder = CertificateHolder.create(dataDir + "temp.pfx", "password");
        DigitalSignatureUtil.sign(dataDir + "Document.NewSignatureLine.docx", dataDir + "Document.NewSignatureLine.docx_out.docx", certHolder, signOptions);
        // ExEnd:CreatingAndSigningNewSignatureLine

        System.out.println("\nDocument is created and Signed with new SignatureLine successfully.\nFile saved at " + dataDir + "Document.NewSignatureLine.docx_out.docx");
    }

    public static void SigningExistingSignatureLine(String dataDir) throws Exception
    {
        // ExStart:SigningExistingSignatureLine
        Document doc = new Document(dataDir + "Document.Signed.docx");
        SignatureLine signatureLine = ((Shape)doc.getFirstSection().getBody().getChild(NodeType.SHAPE, 0, true)).getSignatureLine();

        SignOptions signOptions = new SignOptions();
        signOptions.setSignatureLineId(signatureLine.getId());
        java.nio.file.Path path = java.nio.file.Paths.get(dataDir + "SignatureImage.emf");
        signOptions.setSignatureLineImage(Files.readAllBytes(path));

        CertificateHolder certHolder = CertificateHolder.create(dataDir + "temp.pfx", "password");
        DigitalSignatureUtil.sign(dataDir + "Document.Signed.docx", dataDir + "Document.Signed.ExistingSignatureLine.docx", certHolder, signOptions);
        // ExEnd:SigningExistingSignatureLine

        System.out.println("\nDocument is signed with existing SignatureLine successfully.\nFile saved at " + dataDir + "Document.Signed.ExistingSignatureLine.docx");
    }
}
