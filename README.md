# How to create a CRMF with BouncyCastle

This is a simple Java program that demonstrates how to build a CRMF (Certificate Request Message Format) request using [BouncyCastle](https://www.bouncycastle.org).

> This project is intended as an example for learning purpose, not as a reusable library.

## What does the program do?
- Generates two 256 bit Elliptic Curve key pairs
- Creates two `CertReqMsg`
  - One for TLS client / server authentication
  - One for Signing
- Adds X.509 extensions on each request
- Outputs:
  - CRMF request as a Base64-encoded string
  - ASN.1 dump of the CRMF

## Example Output
```terminaloutput
Asn1 Dump: 
DER Sequence
    DER Sequence
        DER Sequence
            Integer(1)
            DER Sequence
                DER Tagged [CONTEXT 5]
                    DER Sequence
                        DER Set
                            DER Sequence
                                ObjectIdentifier(2.5.4.3)
                                UTF8String(exampleTls) 
                DER Tagged [CONTEXT 6] IMPLICIT 
                    DER Sequence
                        DER Sequence
                            ObjectIdentifier(1.2.840.10045.2.1)
                            ObjectIdentifier(1.2.840.10045.3.1.7)
                        DER Bit String[65, 0] 
                DER Tagged [CONTEXT 9] IMPLICIT 
                    DER Sequence
                        DER Sequence
                            ObjectIdentifier(2.5.29.15)
                            Boolean(true)
                            DER Octet String[4] 
                        DER Sequence
                            ObjectIdentifier(2.5.29.37)
                            Boolean(true)
                            DER Octet String[22] 
        DER Tagged [CONTEXT 1] IMPLICIT 
            DER Sequence
                DER Sequence
                    ObjectIdentifier(1.2.840.10045.4.3.2)
                DER Bit String[71, 0] 
    DER Sequence
        DER Sequence
            Integer(2)
            DER Sequence
                DER Tagged [CONTEXT 5]
                    DER Sequence
                        DER Set
                            DER Sequence
                                ObjectIdentifier(2.5.4.3)
                                UTF8String(exampleSigning) 
                DER Tagged [CONTEXT 6] IMPLICIT 
                    DER Sequence
                        DER Sequence
                            ObjectIdentifier(1.2.840.10045.2.1)
                            ObjectIdentifier(1.2.840.10045.3.1.7)
                        DER Bit String[65, 0] 
                DER Tagged [CONTEXT 9] IMPLICIT 
                    DER Sequence
                        DER Sequence
                            ObjectIdentifier(2.5.29.15)
                            Boolean(true)
                            DER Octet String[4] 
        DER Tagged [CONTEXT 1] IMPLICIT 
            DER Sequence
                DER Sequence
                    ObjectIdentifier(1.2.840.10045.4.3.2)
                DER Bit String[71, 0] 

Crmf Base64:
    MIIB+zCCAQkwga4CAQEwgailFzAVMRMwEQYDVQQDDApleGFtcGxlVGxzplkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEZv2tY5prQl2Nr7ipI9yg2C/jR99LgkMz1pn46RzTQRdqWw5MswLu5tQf0IeYheKd18Z6BDqUMKahPqe7v93Pr6kyMA4GA1UdDwEB/wQEAwIDKDAgBgNVHSUBAf8EFjAUBggrBgEFBQcDAQYIKwYBBQUHAwKhVjAKBggqhkjOPQQDAgNIADBFAiBBZuqmWvT5lSdNJxHQl6D1IIrA6CWWHjfB+QoqQePvKwIhAPby3FK4z2JGRJgRuWvh7eIaHwJQF/RQM6qRpFPYDxqkMIHrMIGQAgECMIGKpRswGTEXMBUGA1UEAwwOZXhhbXBsZVNpZ25pbmemWTATBgcqhkjOPQIBBggqhkjOPQMBBwNCAAQByF9AGolb5YZeOzTqp3t/BXcWCxyQUyS7OojJYXnNg8Y4tsokDYbbN60EcmAv2Wc3O/+J+o6Wj17EmIdZicK6qRAwDgYDVR0PAQH/BAQDAgeAoVYwCgYIKoZIzj0EAwIDSAAwRQIhAPr9WaR5ygIjaJsj/3b0S8coMGlFd2NK2pCihFA8chWoAiBh+3AhfqUgsoDjO6r783r4FUlN3PqOZCWzRwhDv3PlZQ==
```

## References
[RFC-4211 - Certificate Request Message Format](https://datatracker.ietf.org/doc/html/rfc4211)
