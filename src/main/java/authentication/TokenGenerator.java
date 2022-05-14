package authentication;

import java.util.Random;

public class TokenGenerator
{
    public static final int tokenLength = 50;

    public static String createToken()
    {
        String availableCharacters = "1234567890ABCDEFGHJKLMNOPQRSTWXYZabcdefghjklmnopqrstwxyz/*-+.,`!@#$%^&*()_+}|{:";

        StringBuilder token = new StringBuilder();

        Random random = new Random();

        for ( int charIndex = 0; charIndex < tokenLength; charIndex++ )
        {
            token.append(availableCharacters.charAt(random.nextInt(availableCharacters.length())));
        }

        return token.toString();
    }
}
