package Facade;

import JavaBeans.ClientType;

public interface CouponClientFacade {

	public CouponClientFacade login(String name, String password, ClientType clientType);

}
