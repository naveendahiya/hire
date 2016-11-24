(function() {
    'use strict';

    angular
        .module('hireApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('extra-field-settings', {
            parent: 'entity',
            url: '/extra-field-settings',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ExtraFieldSettings'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/extra-field-settings/extra-field-settings.html',
                    controller: 'ExtraFieldSettingsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('extra-field-settings-detail', {
            parent: 'entity',
            url: '/extra-field-settings/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ExtraFieldSettings'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/extra-field-settings/extra-field-settings-detail.html',
                    controller: 'ExtraFieldSettingsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ExtraFieldSettings', function($stateParams, ExtraFieldSettings) {
                    return ExtraFieldSettings.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'extra-field-settings',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('extra-field-settings-detail.edit', {
            parent: 'extra-field-settings-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/extra-field-settings/extra-field-settings-dialog.html',
                    controller: 'ExtraFieldSettingsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ExtraFieldSettings', function(ExtraFieldSettings) {
                            return ExtraFieldSettings.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('extra-field-settings.new', {
            parent: 'extra-field-settings',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/extra-field-settings/extra-field-settings-dialog.html',
                    controller: 'ExtraFieldSettingsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                extraFieldSettingsId: null,
                                fieldName: null,
                                importedId: null,
                                siteId: null,
                                dateCreated: null,
                                dataItemType: null,
                                extraFieldType: null,
                                extraFieldOptions: null,
                                position: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('extra-field-settings', null, { reload: 'extra-field-settings' });
                }, function() {
                    $state.go('extra-field-settings');
                });
            }]
        })
        .state('extra-field-settings.edit', {
            parent: 'extra-field-settings',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/extra-field-settings/extra-field-settings-dialog.html',
                    controller: 'ExtraFieldSettingsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ExtraFieldSettings', function(ExtraFieldSettings) {
                            return ExtraFieldSettings.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('extra-field-settings', null, { reload: 'extra-field-settings' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('extra-field-settings.delete', {
            parent: 'extra-field-settings',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/extra-field-settings/extra-field-settings-delete-dialog.html',
                    controller: 'ExtraFieldSettingsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ExtraFieldSettings', function(ExtraFieldSettings) {
                            return ExtraFieldSettings.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('extra-field-settings', null, { reload: 'extra-field-settings' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
