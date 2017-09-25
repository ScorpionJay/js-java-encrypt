package org.demo.util;
/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Test123.java
 * 
 * @author jay
 * @version 2017年9月21日 下午4:16:55
 */
public class EncryptUtil {
	public static void main(String[] args) {
		EncryptUtil.des(
				"VSKLlt0FudIywhe6/Zl47A=="
				,
				"TT4BQAwFKTuWQAxjQCjl/5CO6drLBwiCUT45DObj+rCx/6xCIxhH6+s2uFCpT8nH/lw21m+YPQxiWrOLxkkflxXAblZKMcBsx4CK63Iy8VHcNcLD6qkym3EKrJrEpchFT5hmEqkyBxzogkCRlUKSCqeOIBx5rytED7xCw116AhI="
		);
	}
	static String praivateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANDEizs5xoIxZCeGn/vlRXWPo1CEM7KBMvfL4XDTGFh/x7MsbuZAWvgxK9vj/ymucCeLVd86JXayqmgt8u9B4x9GuVyL0d6Yvhe+rVvPczjQMnBYZ33GawQcn/hUWXchct3rm3r9pD4VNpZy7taVHre4A6bf/1LtckDXId5CaWT9AgMBAAECgYEAg8vxEjzRQ5Qm5IUrLv15MlSyB7zOXl0Obj26X14FOqnAyy67/ISYaaOxSqrPheLoTy650amFyT/WMNsBSWbRGOM5GPPfzLzVdiPjHa/bBMBeVUIYzB9yQP1X4UqqyIyUR8Bh6e9q6UOkVWBpdtYMEfdr8xqYt65u3xkIGUeRoUkCQQD2BU+dhZq2WeieUi6oer84ExXDghiaIeF4qj7yjBaR9FJgKtdTRZRe5Hnhrmiso7wA/tIyOlaRB/I8+myaFBSbAkEA2TxldHylt2/PfBJA690V5K3teCLsBWBo645HO30xStIcY5hGM606IvAbuKXyCcaU4ekulEc66LsnwgTHpybqRwJAZ9GF72taBmmaiHUVy3NBDmC/ZmmYCDMT+t+dAK2tOJppyLtLcpCCNCkmZd47vd66j5D5EskbjshTqPfkrICc0QJBAMz2KRsxCAH9IpyDdHGV1TQ/zhBNkra38gZd5WOBiWJ4v4RQhdv4EyQnu2AKYkVK8en8YpBQbIptGuP6Mo3JL70CQQCJ92URWBfA4F/n6vnombl9vBcH5o42pWrdrjM7c18OOd54lmn7w3RTFd1mI4qhutJYVllG5yJ+POsB3s8zRNF3";
	public static String des(String s,String key){
		String tdesKey = EncryptRSAUtil.decrypt(key, praivateKey);
		return Encrypt3DESUtil2.decrypt(s,tdesKey);
	}
}
