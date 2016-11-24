(function() {
    'use strict';

    angular
        .module('hireApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('mru', {
            parent: 'entity',
            url: '/mru',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Mrus'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mru/mrus.html',
                    controller: 'MruController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('mru-detail', {
            parent: 'entity',
            url: '/mru/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Mru'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mru/mru-detail.html',
                    controller: 'MruDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Mru', function($stateParams, Mru) {
                    return Mru.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'mru',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('mru-detail.edit', {
            parent: 'mru-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mru/mru-dialog.html',
                    controller: 'MruDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Mru', function(Mru) {
                            return Mru.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('mru.new', {
            parent: 'mru',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mru/mru-dialog.html',
                    controller: 'MruDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                mruId: null,
                                userId: null,
                                siteId: null,
                                dataItemType: null,
                                dataItemText: null,
                                url: null,
                                dateCreated: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('mru', null, { reload: 'mru' });
                }, function() {
                    $state.go('mru');
                });
            }]
        })
        .state('mru.edit', {
            parent: 'mru',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mru/mru-dialog.html',
                    controller: 'MruDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Mru', function(Mru) {
                            return Mru.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('mru', null, { reload: 'mru' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('mru.delete', {
            parent: 'mru',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mru/mru-delete-dialog.html',
                    controller: 'MruDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Mru', function(Mru) {
                            return Mru.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('mru', null, { reload: 'mru' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
