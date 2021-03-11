package com.pandasaza.base.ifs;

import com.pandasaza.base.model.network.Header;

public interface CrudInterface<Req, Res> {

    Header<Res> create(Header<Req> request);

    Header<Res> read(Long id);

    Header<Res> update(Header<Req> request);

    Header delete(Long id);

    //Header<Res> read(Long id1, Long id2);

    //Header delete(Long id1, Long id2);

}
