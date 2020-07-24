(function() {
    'use strict';

    angular
        .module('utmApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('dias-laborales', {
            parent: 'entity',
            url: '/dias-laborales?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'utmApp.diasLaborales.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/dias-laborales/dias-laborales.html',
                    controller: 'DiasLaboralesController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('diasLaborales');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('dias-laborales-detail', {
            parent: 'dias-laborales',
            url: '/dias-laborales/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'utmApp.diasLaborales.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/dias-laborales/dias-laborales-detail.html',
                    controller: 'DiasLaboralesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('diasLaborales');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'DiasLaborales', function($stateParams, DiasLaborales) {
                    return DiasLaborales.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'dias-laborales',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('dias-laborales-detail.edit', {
            parent: 'dias-laborales-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dias-laborales/dias-laborales-dialog.html',
                    controller: 'DiasLaboralesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DiasLaborales', function(DiasLaborales) {
                            return DiasLaborales.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('dias-laborales.new', {
            parent: 'dias-laborales',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dias-laborales/dias-laborales-dialog.html',
                    controller: 'DiasLaboralesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('dias-laborales', null, { reload: 'dias-laborales' });
                }, function() {
                    $state.go('dias-laborales');
                });
            }]
        })
        .state('dias-laborales.edit', {
            parent: 'dias-laborales',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dias-laborales/dias-laborales-dialog.html',
                    controller: 'DiasLaboralesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DiasLaborales', function(DiasLaborales) {
                            return DiasLaborales.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('dias-laborales', null, { reload: 'dias-laborales' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('dias-laborales.delete', {
            parent: 'dias-laborales',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dias-laborales/dias-laborales-delete-dialog.html',
                    controller: 'DiasLaboralesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['DiasLaborales', function(DiasLaborales) {
                            return DiasLaborales.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('dias-laborales', null, { reload: 'dias-laborales' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
