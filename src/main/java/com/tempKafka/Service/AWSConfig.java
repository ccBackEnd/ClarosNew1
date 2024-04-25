
package com.tempKafka.Service;

import com.amazonaws.auth.BasicSessionCredentials;

public interface AWSConfig {

	public BasicSessionCredentials client(String token);

}
