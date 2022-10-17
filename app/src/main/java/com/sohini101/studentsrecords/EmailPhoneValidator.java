package com.sohini101.studentsrecords;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by SOHINI PAL on 30-12-2017.
 */

public class EmailPhoneValidator {
    private Pattern patternemail,patternphone;
    private Matcher matcheremail,matcherphone;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    /*
    ^			#start of the line
  [_A-Za-z0-9-\\+]+	#  must start with string in the bracket [ ], must contains one or more (+)
  (			#   start of group #1
    \\.[_A-Za-z0-9-]+	#     follow by a dot "." and string in the bracket [ ], must contains one or more (+)
  )*			#   end of group #1, this group is optional (*)
    @			#     must contains a "@" symbol
     [A-Za-z0-9-]+      #       follow by string in the bracket [ ], must contains one or more (+)
      (			#         start of group #2 - first level TLD checking
       \\.[A-Za-z0-9]+  #           follow by a dot "." and string in the bracket [ ], must contains one or more (+)
      )*		#         end of group #2, this group is optional (*)
      (			#         start of group #3 - second level TLD checking
       \\.[A-Za-z]{2,}  #           follow by a dot "." and string in the bracket [ ], with minimum length of 2
      )			#         end of group #3
       $			#end of the line
     */
    private static final String PHONE_PATTERN="^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$";
    /*
    ^regex=Finds regex that must match at the beginning of the line.
    parentheses are used for grouping.
    X? finds no or exactly one letter X
    ?: It's called a 'non-capturing group', which means the regex would not make a group by the match inside the parenteses like it would otherwise do (normally, a parenthesis creates a group).
     x+ Finds one or several letter X
     x|z find x or z
     \d{1,4} means \d must occur at least once and at a maximum of four.
     \\s  matches single whitespace character
     * Occurs zero or more times
     [abc][vz] Set definition, can match a or b or c followed by either v or z.
     */
    public EmailPhoneValidator(){

        patternemail=Pattern.compile(EMAIL_PATTERN);
        patternphone=Pattern.compile(PHONE_PATTERN);

    }
    public boolean validateEmail(final String email) {

        matcheremail = patternemail.matcher(email);
        return matcheremail.matches();

    }

    public boolean validatePhone(final String phone){
        matcherphone=patternphone.matcher(phone);
        return matcherphone.matches();
    }


}
