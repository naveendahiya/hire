(function() {
    'use strict';

    angular
        .module('hireApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('attachment', {
            parent: 'entity',
            url: '/attachment',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Attachments'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/attachment/attachments.html',
                    controller: 'AttachmentController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('attachment-detail', {
            parent: 'entity',
            url: '/attachment/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Attachment'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/attachment/attachment-detail.html',
                    controller: 'AttachmentDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Attachment', function($stateParams, Attachment) {
                    return Attachment.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'attachment',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('attachment-detail.edit', {
            parent: 'attachment-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/attachment/attachment-dialog.html',
                    controller: 'AttachmentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Attachment', function(Attachment) {
                            return Attachment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('attachment.new', {
            parent: 'attachment',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/attachment/attachment-dialog.html',
                    controller: 'AttachmentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                attachmentId: null,
                                dataItemId: null,
                                dataItemType: null,
                                siteId: null,
                                title: null,
                                originalFilename: null,
                                storedFilename: null,
                                contentType: null,
                                resume: null,
                                text: null,
                                dateCreated: null,
                                dateModified: null,
                                profileImage: null,
                                directoryName: null,
                                md5Sum: null,
                                fileSizeKb: null,
                                md5SumText: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('attachment', null, { reload: 'attachment' });
                }, function() {
                    $state.go('attachment');
                });
            }]
        })
        .state('attachment.edit', {
            parent: 'attachment',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/attachment/attachment-dialog.html',
                    controller: 'AttachmentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Attachment', function(Attachment) {
                            return Attachment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('attachment', null, { reload: 'attachment' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('attachment.delete', {
            parent: 'attachment',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/attachment/attachment-delete-dialog.html',
                    controller: 'AttachmentDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Attachment', function(Attachment) {
                            return Attachment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('attachment', null, { reload: 'attachment' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
