import {Injector} from 'ngES6';

export default class CooperatorService extends Injector {
    static $inject = ['$http', 'Settings'];

    get uploadPhotoUrl() {
        return '/api/web/admin/cooperator/photo';
    }

    search(params, page, pageSize) {
        const {$http, Settings} = this.$injected;
        return $http.get(Settings.domain + '/api/web/admin/cooperator', {params: {...params, page: page, pageSize: pageSize}});
    }

    getDetail(id) {
        const {$http, Settings} = this.$injected;
        return $http.get(Settings.domain + '/api/web/admin/cooperator/' + id);
    }

    verify(id, verified, verifyMsg) {
        const {$http, Settings} = this.$injected;
        return $http.post(Settings.domain + '/api/web/admin/cooperator/verify/' + id, {verified: verified, remark: verifyMsg});
    }

    mapLocations(province, city, page, pageSize) {
        const {$http, Settings} = this.$injected;
        return $http.get(Settings.domain + '/api/web/admin/cooperator/mapview', {params: {province: province, city: city, page: page, pageSize: pageSize}});
    }

    add(coop) {
        const {$http, Settings} = this.$injected;
        return $http.post(Settings.domain + '/api/web/admin/cooperator', coop);
    }

    update(coop) {
        const {$http, Settings} = this.$injected;
        return $http.post(Settings.domain + '/api/web/admin/cooperator/' + coop.id, coop);
    }
}
